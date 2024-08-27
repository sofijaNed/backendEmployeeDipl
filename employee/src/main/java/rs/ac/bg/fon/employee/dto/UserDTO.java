package rs.ac.bg.fon.employee.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import rs.ac.bg.fon.employee.entity.Role;

@Data
public class UserDTO {
    private Integer userID;

    private String username;

    private Role role;
}
