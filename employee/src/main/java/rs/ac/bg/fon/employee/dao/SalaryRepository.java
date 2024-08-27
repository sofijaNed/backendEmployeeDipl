package rs.ac.bg.fon.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.employee.entity.Salary;
import rs.ac.bg.fon.employee.entity.complexprimarykey.SalaryCPK;

import java.util.List;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, SalaryCPK> {
    List<Salary> findByEmployeeID(Integer employeeID);
}
