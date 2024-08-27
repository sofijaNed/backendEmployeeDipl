package rs.ac.bg.fon.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdministratorDTO {

    private Integer adminID;

    @NotBlank(message =  "Polje Ime je obavezno.")
    private String firstName;

    @NotBlank(message =  "Polje Prezime je obavezno.")
    private String lastName;

    @NotBlank(message =  "Polje email je obavezno.")
    @Email(message = "Email mora biti u validnom formatu.")
    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDTO userDTO;

}
