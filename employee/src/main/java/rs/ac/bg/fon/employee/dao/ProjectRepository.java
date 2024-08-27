package rs.ac.bg.fon.employee.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.bg.fon.employee.entity.Employee;
import rs.ac.bg.fon.employee.entity.Project;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByProjectId(Integer projectID);
    List<Project> findByTeamID(Integer teamID);

}
