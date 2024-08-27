package rs.ac.bg.fon.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="administrator")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Administrator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adminID")
    private Integer adminID;


    @Column(name = "firstName")
    private String firstName;


    @Column(name = "lastName")
    private String lastName;


    @Column(name = "email")
    private String email;




    @JoinColumn(name="userID",referencedColumnName = "userID")
    @ManyToOne(optional = false)
    @JsonIgnore
    private User userAdmin;
}
