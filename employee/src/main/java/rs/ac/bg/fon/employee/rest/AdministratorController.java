package rs.ac.bg.fon.employee.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.bg.fon.employee.dto.AdministratorDTO;
import rs.ac.bg.fon.employee.dto.TimeOffRequestDTO;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.implementation.AdministratorImpl;

import java.util.List;

@RestController
@RequestMapping("/administrators")
public class AdministratorController {

    private AdministratorImpl adminImpl;

    @Autowired
    public AdministratorController(AdministratorImpl adminImpl) {
        this.adminImpl = adminImpl;
    }

    @GetMapping
    public List<AdministratorDTO> findAll(){
        return adminImpl.findAll();
    }


    @GetMapping("{id}")
    public ResponseEntity<AdministratorDTO> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(adminImpl.findById(id));
    }


    @PostMapping
    public ResponseEntity<AdministratorDTO> save(@Valid @RequestBody AdministratorDTO administratorDTO) throws Exception {
        return new ResponseEntity<>(adminImpl.save(administratorDTO), HttpStatus.CREATED);
    }


    @PutMapping
    public ResponseEntity<AdministratorDTO> update(@Valid @RequestBody AdministratorDTO administratorDTO) throws Exception {
        return new ResponseEntity<>(adminImpl.save(administratorDTO), HttpStatus.OK);
    }



}
