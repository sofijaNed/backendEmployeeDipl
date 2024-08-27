package rs.ac.bg.fon.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.employee.entity.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    List<Employee> findByEmail(String email);
    List<Employee> findByEmployeeID(Integer employeeID);
    List<Employee> findByTeamID(Integer teamID);
}

