package rs.ac.bg.fon.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.employee.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
