package rs.ac.bg.fon.employee.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.employee.dao.AdministratorRepository;
import rs.ac.bg.fon.employee.dao.EmployeeRepository;
import rs.ac.bg.fon.employee.dao.UserRepository;
import rs.ac.bg.fon.employee.entity.Administrator;
import rs.ac.bg.fon.employee.entity.Employee;
import rs.ac.bg.fon.employee.entity.User;
import rs.ac.bg.fon.employee.security.config.JWTService;
import rs.ac.bg.fon.employee.security.token.Token;
import rs.ac.bg.fon.employee.security.token.TokenRepository;
import rs.ac.bg.fon.employee.entity.Role;
import rs.ac.bg.fon.employee.security.token.TokenType;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final EmployeeRepository employeeRepository;

    private final AdministratorRepository administratorRepository;


    private final TokenRepository tokenRepository;


    private final PasswordEncoder passwordEncoder;


    private final JWTService jwtService;


    private final AuthenticationManager authenticationManager;




    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new BadCredentialsException("Neispravno uneti kredencijali"));

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user,jwtToken);

        Integer id;
        String firstname;
        String lastname;
        String email;
        String role;

        if(!user.getUsername().contains("@admin.fon.bg.ac.rs")){
            Employee employee = (Employee) employeeRepository.findByEmail(user.getUsername());
            id = employee.getEmployeeID();
            firstname = employee.getFirstName();
            lastname = employee.getLastName();
            email = employee.getEmail();
            role=Role.USER.name();
        }
        else{
            Administrator admin = administratorRepository.findByEmail(user.getUsername());
            id = admin.getAdminID();
            firstname = admin.getFirstName();
            lastname = admin.getLastName();
            email = admin.getEmail();
            role = Role.ADMIN.name();
        }

        return AuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .email(email)
                .role(role)
                .message("Uspesno ste se ulogovali")
                .build();
    }


//    public ResponseEntity<String> changePassword(RequestChangePassword request) throws Exception {
//        Optional<User> dbUser = userRepository.findByUsername(request.getUsername());
//        Optional<Token> token = tokenRepository.findByToken(request.getToken());
//        if(dbUser.isPresent() && token.isPresent()){
//            User user = dbUser.get();
//            if(request.getOldPassword()!=null) {
//                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getOldPassword()));
//            }
//            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
//            userRepository.save(member);
//        }
//        else{
//            throw new NotFoundException("Korisnik sa datim korisnickim imenom nije pronadjen: " + request.getUsername());
//        }
//        return ResponseEntity.status(HttpStatus.OK).body("Korisnik je uspesno promenio lozinku");
//    }


    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if(userEmail!=null){
            var user = this.userRepository.findByUsername(userEmail)
                    .orElseThrow();
            if(jwtService.isTokenValid(refreshToken,user)){
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user,accessToken);
                var authResponse = AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(),authResponse);
            }
        }
    }


    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByMember(user.getUserID());
        System.out.println(validUserTokens);
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }


    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
