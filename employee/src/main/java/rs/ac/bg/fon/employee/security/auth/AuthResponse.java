package rs.ac.bg.fon.employee.security.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private Integer id;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accessToken;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String refreshToken;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String firstname;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastname;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String email;



    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String role;
}
