package rs.ac.bg.fon.employee.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="projectID")
    private Integer projectID;

    @Column(name="projectName")
    private String projectName;

    @Column(name="description")
    private String description;

    @Column(name="startDate")
    private LocalDate startDate;

    @Column(name="endDate")
    private LocalDate endDate;

    @JoinColumn(name="teamID",referencedColumnName = "teamID")
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Team team;

    @OneToMany(mappedBy = "project")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Collection<EmploymentProjectAssignment> assignments;
}
