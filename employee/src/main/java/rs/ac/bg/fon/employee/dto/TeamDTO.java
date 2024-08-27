package rs.ac.bg.fon.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Collection;

@Data
public class TeamDTO {
    private Integer teamID;

    @NotBlank(message =  "Polje naziv tima je obavezno")
    private String teamName;

    @NotBlank(message =  "Polje maksimalni broj clanova tima je obavezno")
    private Integer maxNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<EmployeeDTO> employees;
}
