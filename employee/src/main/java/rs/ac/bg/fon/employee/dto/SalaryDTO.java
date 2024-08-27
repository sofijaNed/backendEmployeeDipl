package rs.ac.bg.fon.employee.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import rs.ac.bg.fon.employee.entity.complexprimarykey.SalaryCPK;

import java.time.LocalDate;

@Data
public class SalaryDTO {

    protected SalaryCPK salaryCPK;


    @NotBlank(message = "Polje plata  je obavezno")
    private double salaryAmount;


    @NotBlank(message = "Polje datum naplate  je obavezno")
    private LocalDate paymentDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnore
    private EmployeeDTO employeeDTO;
}
