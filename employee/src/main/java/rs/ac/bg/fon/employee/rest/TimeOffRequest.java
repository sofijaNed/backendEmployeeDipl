package rs.ac.bg.fon.employee.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.employee.dto.TimeOffRequestDTO;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.implementation.TimeOffRequestImpl;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/requests")
public class TimeOffRequest {

    private TimeOffRequestImpl requestImpl;

    @Autowired
    public TimeOffRequest(TimeOffRequestImpl requestImpl) {
        this.requestImpl = requestImpl;
    }

    @GetMapping
    public List<TimeOffRequestDTO> findAll(){
        return requestImpl.findAll();
    }


    @GetMapping("{id}")
    public ResponseEntity<TimeOffRequestDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(requestImpl.findById(id));
    }


    @PostMapping
    public ResponseEntity<TimeOffRequestDTO> save(@Valid @RequestBody TimeOffRequestDTO requestDTO) throws Exception {
        return new ResponseEntity<>(requestImpl.save(requestDTO), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<TimeOffRequestDTO> update(@Valid @RequestBody TimeOffRequestDTO timeOffRequestDTO) throws Exception {
        return new ResponseEntity<>(requestImpl.save(timeOffRequestDTO), HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Integer id) throws Exception {
        requestImpl.deleteById(id);
        return new ResponseEntity<>("Deleted",HttpStatus.OK);
    }
}
