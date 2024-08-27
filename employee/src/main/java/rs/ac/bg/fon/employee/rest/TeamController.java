package rs.ac.bg.fon.employee.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.employee.dto.EmployeeDTO;
import rs.ac.bg.fon.employee.dto.ProjectDTO;
import rs.ac.bg.fon.employee.dto.TeamDTO;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.implementation.EmployeeImpl;
import rs.ac.bg.fon.employee.service.implementation.ProjectImpl;
import rs.ac.bg.fon.employee.service.implementation.TeamImpl;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private TeamImpl teamImpl;

    private EmployeeImpl employeeImpl;

    private ProjectImpl projectImpl;

    @Autowired
    public TeamController(TeamImpl teamImpl, EmployeeImpl employeeImpl, ProjectImpl projectImpl) {
        this.teamImpl = teamImpl;
        this.employeeImpl = employeeImpl;
        this.projectImpl = projectImpl;
    }

    @GetMapping
    public List<TeamDTO> findAll(){
        return teamImpl.findAll();
    }


    @GetMapping("{id}")
    public ResponseEntity<TeamDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(teamImpl.findById(id));
    }


    @PostMapping
    public ResponseEntity<TeamDTO> save(@Valid @RequestBody TeamDTO teamDTO) throws Exception {
        return new ResponseEntity<>(teamImpl.save(teamDTO), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<TeamDTO> update(@Valid @RequestBody TeamDTO teamDTO) throws Exception {
        return new ResponseEntity<>(teamImpl.save(teamDTO), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
        teamImpl.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }


    @GetMapping("/{id}/employees")
    public List<EmployeeDTO> getEmployees(@PathVariable("id") Integer id) throws NotFoundException {
        return employeeImpl.getEmployees(id);
    }

    @GetMapping("/{id}/projects")
    public List<ProjectDTO> getProjects(@PathVariable("id") Integer id) throws NotFoundException {
        return projectImpl.getProjects(id);
    }
}
