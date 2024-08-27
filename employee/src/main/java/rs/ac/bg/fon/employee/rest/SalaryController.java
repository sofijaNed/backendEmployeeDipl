package rs.ac.bg.fon.employee.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.employee.dto.SalaryDTO;
import rs.ac.bg.fon.employee.entity.complexprimarykey.SalaryCPK;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.implementation.SalaryImpl;

import java.util.List;

@RestController
@RequestMapping("/salaries")
public class SalaryController {

    private SalaryImpl salaryImpl;

    @Autowired
    public SalaryController(SalaryImpl salaryImpl) {
        this.salaryImpl = salaryImpl;
    }

    @GetMapping
    public List<SalaryDTO> findAll(){
        return salaryImpl.findAll();
    }

    @GetMapping("/{salaryID}/employee/{employeeID}")
    public ResponseEntity<SalaryDTO> findById(@PathVariable("salaryID") Integer salaryID, @PathVariable("employeeID") Integer employeeID) throws NotFoundException {
        return ResponseEntity.ok().body(salaryImpl.findById(new SalaryCPK(salaryID,employeeID)));
    }

    @PostMapping
    public ResponseEntity<SalaryDTO> save(@Valid @RequestBody SalaryDTO salaryDTO) throws Exception {
        return new ResponseEntity<>(salaryImpl.save(salaryDTO), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<SalaryDTO> update(@Valid @RequestBody SalaryDTO salaryDTO) throws Exception {
        return new ResponseEntity<>(salaryImpl.save(salaryDTO), HttpStatus.OK);
    }


    @DeleteMapping("/{salaryID}/employee/{employeeID}")
    public ResponseEntity<String> deleteById(@PathVariable("salaryID") Integer salaryID, @PathVariable("employeeID")Integer employeeID) throws Exception {
        salaryImpl.deleteById(new SalaryCPK(salaryID,employeeID));
        return new ResponseEntity<>("Odgovor je izbrisan",HttpStatus.OK);
    }


    @GetMapping("/{questionId}")
    public List<SalaryDTO> getSalariesFromEmployees(@PathVariable("employeeID") Integer employeeID){
        return salaryImpl.getSalaries(employeeID);
    }
}
