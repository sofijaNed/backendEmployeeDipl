package rs.ac.bg.fon.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name = "employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employeeID")
    private Integer employeeID;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="email")
    private String email;


    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name="contactNumber")
    private String contactNumber;

    @Column(name="address")
    private String address;

    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Collection<Salary> salary;

    @JoinColumn(name="teamID",referencedColumnName = "teamID")
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Team team;

    @JoinColumn(name="userID",referencedColumnName = "userID")
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Collection<EmploymentProjectAssignment> assignments;
}
