package rs.ac.bg.fon.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class ProjectDTO {
    private Integer projectID;

    @NotBlank(message =  "Polje naziv projekta je obavezno")
    private String projectName;

    @NotBlank(message =  "Polje opis je obavezno")
    private String description;

    @NotBlank(message =  "Polje datum pocetka je obavezno")
    @PastOrPresent(message = "Datum pocetka ne sme biti posle datuma kraja.")
    private LocalDate startDate;

    @NotBlank(message =  "Polje datum kraja je obavezno")
    @PastOrPresent(message = "Datum kraja ne sme biti pre pocetka")
    private LocalDate endDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private TeamDTO teamDTO;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Collection<EmploymentProjectAssignmentDTO> assignments;
}
