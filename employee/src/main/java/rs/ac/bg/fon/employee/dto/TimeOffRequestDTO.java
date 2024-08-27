package rs.ac.bg.fon.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import rs.ac.bg.fon.employee.entity.Employee;

import java.time.LocalDate;

@Data
public class TimeOffRequestDTO {

    private Integer requestID;

    @NotBlank(message =  "Polje datum pocetka je obavezno")
    @PastOrPresent(message = "Datum pocetka ne sme biti posle datuma kraja.")
    private LocalDate startDate;

    @NotBlank(message =  "Polje datum kraja je obavezno")
    @PastOrPresent(message = "Datum pocetka ne sme biti pre datuma kraja.")
    private LocalDate endDate;

    @NotBlank(message =  "Polje razlog projekta je obavezno")
    private String reason;

    @NotBlank(message =  "Polje status je obavezno")
    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private EmployeeDTO employeeDTO;
}
