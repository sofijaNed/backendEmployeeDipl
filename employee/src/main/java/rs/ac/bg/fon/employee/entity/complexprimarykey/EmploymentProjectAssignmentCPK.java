package rs.ac.bg.fon.employee.entity.complexprimarykey;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Basic;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmploymentProjectAssignmentCPK implements Serializable {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer assignmentID;


    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Basic(optional = false)
    private Integer employeeID;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Basic(optional = false)
    private Integer projectID;
}
