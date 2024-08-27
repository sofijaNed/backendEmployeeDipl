package rs.ac.bg.fon.employee.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.employee.dto.EmployeeDTO;
import rs.ac.bg.fon.employee.dto.EmploymentProjectAssignmentDTO;
import rs.ac.bg.fon.employee.dto.ProjectDTO;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.implementation.ProjectImpl;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private ProjectImpl projectImpl;



    @Autowired
    public ProjectController(ProjectImpl projectImpl) {
        this.projectImpl = projectImpl;

    }

    @GetMapping
    public List<ProjectDTO> findAll(){
        return projectImpl.findAll();
    }


    @GetMapping("{id}")
    public ResponseEntity<ProjectDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(projectImpl.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> save(@Valid @RequestBody ProjectDTO projectDTO) throws Exception {
        return new ResponseEntity<>(projectImpl.save(projectDTO), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<ProjectDTO> update(@Valid @RequestBody ProjectDTO projectDTO) throws Exception {
        return new ResponseEntity<>(projectImpl.update(projectDTO), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
        projectImpl.deleteById(id);
        return new ResponseEntity<>("Projekat je izbrisan",HttpStatus.OK);
    }

    @GetMapping("/{projectID}/employees")
    public List<EmployeeDTO> getEmployeesOnProjects(@PathVariable Integer projectID) throws NotFoundException {
        return projectImpl.getEmployees(projectID);
    }


    @GetMapping("/{id}/assignments")
    public List<EmploymentProjectAssignmentDTO> getResults(@PathVariable Integer id) throws NotFoundException {
        return projectImpl.getResults(id);
    }


    @PostMapping("/assignments")
    public ResponseEntity<EmploymentProjectAssignmentDTO> saveProjectAssignment(@Valid @RequestBody EmploymentProjectAssignmentDTO assignmentDTO){
        return new ResponseEntity<>(projectImpl.saveAssignments(assignmentDTO),HttpStatus.CREATED);
    }


    @PutMapping("/assignments")
    public ResponseEntity<EmploymentProjectAssignmentDTO> updateProjectAssignment(@Valid @RequestBody EmploymentProjectAssignmentDTO assignmentDTO){
        return new ResponseEntity<>(projectImpl.saveAssignments(assignmentDTO),HttpStatus.OK);
    }

    @DeleteMapping("/{projectID}/employees/{employeeID}")
    public ResponseEntity<String> deleteProjectAssignment(@PathVariable("projectID") Integer projectID, @PathVariable("employeeID") Integer employeeID) throws NotFoundException {
        projectImpl.deleteResultExam(projectID, employeeID);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }



}
