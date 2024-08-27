package rs.ac.bg.fon.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import rs.ac.bg.fon.employee.entity.EmploymentProjectAssignment;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class EmployeeDTO {

    private Integer employeeID;

    @NotBlank(message = "Polje ime  je obavezno")
    private String firstName;

    @NotBlank(message = "Polje prezime  je obavezno")
    private String lastName;

    @NotBlank(message = "Polje email  je obavezno")
    private String email;


    @NotBlank(message = "Polje datum rodjenja  je obavezno")
    @PastOrPresent(message = "Datum rodjenja ne moze biti kasnije od trenutnog datuma")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Polje broj telefona  je obavezno")
    private String contactNumber;

    @NotBlank(message = "Polje adrese  je obavezno")
    private String address;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<SalaryDTO> salaries;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TeamDTO teamDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UserDTO userDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<EmploymentProjectAssignmentDTO> assignments;
}
