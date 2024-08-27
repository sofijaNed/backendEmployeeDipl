package rs.ac.bg.fon.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.employee.entity.EmploymentProjectAssignment;
import rs.ac.bg.fon.employee.entity.Salary;
import rs.ac.bg.fon.employee.entity.complexprimarykey.EmploymentProjectAssignmentCPK;

import java.util.List;


@Repository
public interface EmploymentProjectAssignmentRepository extends JpaRepository<EmploymentProjectAssignment, EmploymentProjectAssignmentCPK> {
    List<EmploymentProjectAssignment> findByEmployeeID(Integer employeeID);
    List<EmploymentProjectAssignment> findByProjectID(Integer projectID);
}
