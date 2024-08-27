package rs.ac.bg.fon.employee.security.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @Email(message = "Email mora biti u predefinisanom formatu")
    private String username;


    @NotBlank(message = "Lozinka ne sme biti null")
    private String password;
}