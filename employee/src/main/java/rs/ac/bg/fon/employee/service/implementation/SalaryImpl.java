package rs.ac.bg.fon.employee.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.ac.bg.fon.employee.dao.EmployeeRepository;
import rs.ac.bg.fon.employee.dao.SalaryRepository;
import rs.ac.bg.fon.employee.dto.EmployeeDTO;
import rs.ac.bg.fon.employee.dto.SalaryDTO;
import rs.ac.bg.fon.employee.entity.Salary;
import rs.ac.bg.fon.employee.entity.complexprimarykey.SalaryCPK;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalaryImpl implements ServiceInterface<SalaryDTO> {

    private SalaryRepository salaryRepository;

    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    @Autowired
    public SalaryImpl(SalaryRepository salaryRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.salaryRepository = salaryRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SalaryDTO> findAll() {
        List<Salary> salaries = salaryRepository.findAll();
        List<SalaryDTO> salaryDTOS = new ArrayList<>();
        for(Salary salary:salaries){
            EmployeeDTO employeeDTO = modelMapper.map(salary.getEmployee(),EmployeeDTO.class);
            SalaryDTO salaryDTO = modelMapper.map(salary,SalaryDTO.class);
            salaryDTO.setEmployeeDTO(employeeDTO);
            salaryDTOS.add(salaryDTO);
        }
        return salaryDTOS;
    }

    @Override
    public SalaryDTO findById(Object id) throws NotFoundException {
        SalaryCPK salaryCPK = (SalaryCPK) id;
        Optional<Salary> salary = salaryRepository.findById(salaryCPK);
        SalaryDTO salaryDTO;
        if(salary.isPresent()){
            salaryDTO = modelMapper.map(salary.get(),SalaryDTO.class);
        }
        else{
            throw new NotFoundException("Plata nije pronadjena");
        }
        return salaryDTO;
    }

    @Override
    public SalaryDTO save(SalaryDTO salaryDTO) throws Exception {
        if (salaryDTO == null) {
            throw new NullPointerException("Plata ne moze biti null");
        }
        Salary salary = modelMapper.map(salaryDTO,Salary.class);
        Salary savedSalary = salaryRepository.save(salary);
        return modelMapper.map(savedSalary, SalaryDTO.class);
    }

    @Override
    public SalaryDTO update(SalaryDTO salaryDTO) throws Exception {
        if(salaryDTO==null){
            throw new NullPointerException("Plata ne moze biti null");
        }
        Optional<Salary> dbSalary = salaryRepository.findById(salaryDTO.getSalaryCPK());
        if(dbSalary.isPresent()) {
            Salary salary = modelMapper.map(salaryDTO,Salary.class);
            Salary savedSalary = salaryRepository.save(salary);
            return modelMapper.map(savedSalary, SalaryDTO.class);
        }
        else{
            throw new NotFoundException("Plata nije pronadjena");
        }
    }

    @Override
    public void deleteById(Object id) throws Exception {
        SalaryCPK salaryCPK = (SalaryCPK) id;
        if(!salaryRepository.findById(salaryCPK).isPresent()){
            throw new NotFoundException("Plata nije pronadjena");
        }
        salaryRepository.deleteById(salaryCPK);

    }

    public List<SalaryDTO> getSalaries(Integer id){
        List<Salary> salaries = salaryRepository.findByEmployeeID(id);
        List<SalaryDTO> salaryDTOS = salaries.stream().map(salary->modelMapper.map(salary,SalaryDTO.class))
                .collect(Collectors.toList());
        return salaryDTOS;
    }
}
