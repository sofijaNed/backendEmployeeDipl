package rs.ac.bg.fon.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import rs.ac.bg.fon.employee.entity.complexprimarykey.SalaryCPK;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name="salary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Salary implements Serializable {

    @EmbeddedId
    protected SalaryCPK salaryCPK;


    @Column(name="salaryAmount")
    private double salaryAmount;


    @Column(name="paymentDate")
    private LocalDate paymentDate;


    @JoinColumn(name="employeeID",insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Employee employee;

    public Salary(SalaryCPK salaryCPK){
        this.salaryCPK = salaryCPK;
    }

    public Salary(Integer id,Integer employeeID){
        this.salaryCPK = new SalaryCPK(id,employeeID);
    }

    public Salary(SalaryCPK salaryCPK, double salaryAmount, LocalDate paymentDate) {
        this.salaryCPK = salaryCPK;
        this.salaryAmount = salaryAmount;
        this.paymentDate = paymentDate;
    }
}
