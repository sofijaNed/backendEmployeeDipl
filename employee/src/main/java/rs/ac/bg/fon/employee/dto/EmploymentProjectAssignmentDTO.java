package rs.ac.bg.fon.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import rs.ac.bg.fon.employee.entity.complexprimarykey.EmploymentProjectAssignmentCPK;

@Data
public class EmploymentProjectAssignmentDTO {
    private EmploymentProjectAssignmentCPK assignmentCPK;

    @NotBlank(message = "Polje opis  je obavezno")
    private String description;

    @NotBlank(message = "Polje status  je obavezno")
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private ProjectDTO projectDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private EmployeeDTO employeeDTO;
}
