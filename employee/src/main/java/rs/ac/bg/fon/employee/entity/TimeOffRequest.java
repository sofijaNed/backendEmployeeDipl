package rs.ac.bg.fon.employee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "timeoffrequest")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeOffRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="requestID")
    private Integer requestID;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    private String status;

    @JoinColumn(name="employeeID",referencedColumnName = "employeeID")
    @ManyToOne(optional = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee employee;


}
