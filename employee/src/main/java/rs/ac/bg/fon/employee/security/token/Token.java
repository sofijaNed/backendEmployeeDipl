package rs.ac.bg.fon.employee.security.token;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.bg.fon.employee.entity.User;

import java.io.Serializable;

@Entity
@Table(name="token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;


    @Column(name="token",unique = true)
    public String token;


    @Enumerated(EnumType.STRING)
    @Column(name="tokenType")
    public TokenType tokenType = TokenType.BEARER;


    public boolean revoked;


    public boolean expired;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userID",referencedColumnName = "userID")
    public User user;
}
