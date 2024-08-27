package rs.ac.bg.fon.employee.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authenticationService;





    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authentication(@Valid @RequestBody AuthRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


//    @PostMapping("/changePassword")
//    public ResponseEntity<String> changePassword(@Valid @RequestBody RequestChangePassword request) throws Exception {
//        return authenticationService.changePassword(request);
//    }


    @PostMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
