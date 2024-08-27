package rs.ac.bg.fon.employee.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.employee.dto.EmployeeDTO;
import rs.ac.bg.fon.employee.dto.EmploymentProjectAssignmentDTO;
import rs.ac.bg.fon.employee.dto.SalaryDTO;
import rs.ac.bg.fon.employee.dto.TimeOffRequestDTO;
import rs.ac.bg.fon.employee.entity.Employee;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.implementation.EmployeeImpl;
import rs.ac.bg.fon.employee.service.implementation.ProjectImpl;
import rs.ac.bg.fon.employee.service.implementation.SalaryImpl;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private EmployeeImpl employeeImpl;

    private SalaryImpl salaryImpl;

    private ProjectImpl projectImpl;

    @Autowired
    public EmployeeController(EmployeeImpl employeeImpl, SalaryImpl salaryImpl, ProjectImpl projectImpl) {
        this.employeeImpl = employeeImpl;
        this.salaryImpl = salaryImpl;
        this.projectImpl = projectImpl;
    }

    @GetMapping
    public List<EmployeeDTO> findAll(){
        return employeeImpl.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(employeeImpl.findById(id));
    }


    @PostMapping
    public ResponseEntity<EmployeeDTO> save(@Valid @RequestBody EmployeeDTO employeeDTO) throws Exception {
        return new ResponseEntity<>(employeeImpl.save(employeeDTO), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<EmployeeDTO> update(@Valid @RequestBody EmployeeDTO employeeDTO) throws Exception {
        return new ResponseEntity<>(employeeImpl.update(employeeDTO), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
        employeeImpl.deleteById(id);
        return new ResponseEntity<>("Zaposleni je izbrisan",HttpStatus.OK);
    }

    @GetMapping("/{id}/salaries")
    public List<SalaryDTO> getSalaries(@PathVariable("id")Integer id) throws NotFoundException {
        return salaryImpl.getSalaries(id);
    }

    @GetMapping("/{id}/requests")
    public List<TimeOffRequestDTO> getRequests(@PathVariable("id")Integer id) throws NotFoundException {
        return employeeImpl.getRequests(id);
    }


    @GetMapping("/{id}/assignments")
    public List<EmploymentProjectAssignmentDTO> getAssignments(@PathVariable("id")Integer employeeID) throws NotFoundException {
        return employeeImpl.getResults(employeeID);
    }


    @PostMapping("/assignments")
    public ResponseEntity<EmploymentProjectAssignmentDTO> saveAssignments(@Valid @RequestBody EmploymentProjectAssignmentDTO employmentProjectAssignmentDTO){
        return new ResponseEntity<>(projectImpl.saveAssignments(employmentProjectAssignmentDTO),HttpStatus.CREATED);
    }


    @PutMapping("/assignments")
    public ResponseEntity<EmploymentProjectAssignmentDTO> updateAssignemts(@Valid @RequestBody EmploymentProjectAssignmentDTO employmentProjectAssignmentDTO){
        return new ResponseEntity<>(projectImpl.saveAssignments(employmentProjectAssignmentDTO),HttpStatus.OK);
    }


    @DeleteMapping("/{employeeID}/assignments/{projectID}")
    public ResponseEntity<String> deleteAssignments(@PathVariable("employeeID") Integer employeeID, @PathVariable("projectID") Integer projectID) throws NotFoundException {
        projectImpl.deleteResultExam(projectID, employeeID);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
