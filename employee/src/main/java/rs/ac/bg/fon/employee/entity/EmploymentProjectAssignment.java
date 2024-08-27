package rs.ac.bg.fon.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import rs.ac.bg.fon.employee.entity.complexprimarykey.EmploymentProjectAssignmentCPK;

import java.io.Serializable;

@Entity
@Table(name = "employmentprojectassignment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmploymentProjectAssignment implements Serializable {

    @EmbeddedId
    private EmploymentProjectAssignmentCPK assignmentCPK;

    @Column(name="description")
    private String description;

    @Column(name="status")
    private String status;

    @JoinColumn(name="employeeID",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee employee;

    @JoinColumn(name="employeeID",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Project project;

    public EmploymentProjectAssignment(EmploymentProjectAssignmentCPK assignmentCPK){
        this.assignmentCPK = assignmentCPK;
    }

    public EmploymentProjectAssignment(Integer id,Integer employeeID, Integer projectID){
        this.assignmentCPK = new EmploymentProjectAssignmentCPK(id,employeeID, projectID);
    }

    public EmploymentProjectAssignment(EmploymentProjectAssignmentCPK assignmentCPK, String description, String status) {
        this.assignmentCPK = assignmentCPK;
        this.description = description;
        this.status = status;
    }
}
