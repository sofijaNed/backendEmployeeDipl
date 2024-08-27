package rs.ac.bg.fon.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "team")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamID")
    private Integer teamID;

    @Column(name = "teamName")
    private String teamName;

    @Column(name = "maxNumber")
    private Integer maxNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team")
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Collection<Employee> employees;
}
