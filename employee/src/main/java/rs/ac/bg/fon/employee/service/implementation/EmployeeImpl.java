package rs.ac.bg.fon.employee.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.employee.dao.*;
import rs.ac.bg.fon.employee.dto.*;
import rs.ac.bg.fon.employee.entity.*;
import rs.ac.bg.fon.employee.entity.complexprimarykey.SalaryCPK;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.ServiceInterface;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeImpl implements ServiceInterface<EmployeeDTO> {

    private EmployeeRepository employeeRepository;

    private EmploymentProjectAssignmentRepository assignmentRepository;

    private UserRepository userRepository;
    private SalaryRepository salaryRepository;

    private SalaryImpl salaryIml;

    private TimeOffRequestRepository requestRepository;

    //private AuthenticationService authenticationService;
    //private PasswordEncoder passwordEncoder;



    private ModelMapper modelMapper;

    @Autowired
    public EmployeeImpl(EmployeeRepository employeeRepository,
                        EmploymentProjectAssignmentRepository assignmentRepository,
                        UserRepository userRepository,
                          ModelMapper modelMapper, SalaryImpl salaryIml,
                        TimeOffRequestRepository requestRepository) {
        this.employeeRepository = employeeRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
        this.salaryIml = salaryIml;
        this.modelMapper = modelMapper;
        this.requestRepository = requestRepository;
    }

    @Override
    public List<EmployeeDTO> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for(Employee employee:employees){
            TeamDTO teamDTO = modelMapper.map(employee.getTeam(),TeamDTO.class);
            EmployeeDTO employeeDTO = modelMapper.map(employee,EmployeeDTO.class);
            employeeDTO.setTeamDTO(teamDTO);
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }


    @Override
    public EmployeeDTO findById(Object id) throws NotFoundException {
        Optional<Employee> employee = employeeRepository.findById((Integer) id);
        EmployeeDTO employeeDTO;
        if(employee.isPresent()){
            employeeDTO = modelMapper.map(employee.get(),EmployeeDTO.class);
            Collection<SalaryDTO> salaries = salaryIml.getSalaries(employeeDTO.getEmployeeID());
            employeeDTO.setSalaries(salaries);
            Collection<EmploymentProjectAssignmentDTO> results = getResults((Integer) id);
            employeeDTO.setAssignments(results);
        }
        else{
            throw new NotFoundException("Zaposleni nije pronadjen");
        }
        return employeeDTO;
    }

    @Override
    public EmployeeDTO save(EmployeeDTO employeeDTO) throws Exception {
        if(employeeDTO == null){
            throw new NullPointerException("Zaposleni ne moze biti null");
        }
        if(employeeDTO.getSalaries().isEmpty()){
            throw new IllegalArgumentException("Zaposleni mora imati platu.");
        }
        EmployeeDTO newEmployeeDTO = employeeDTO;

        Employee employee = Employee.builder().firstName(employeeDTO.getFirstName())
                .lastName(employeeDTO.getLastName())
                .email(employeeDTO.getEmail())
                .dateOfBirth(employeeDTO.getDateOfBirth())
                .contactNumber(employeeDTO.getContactNumber())
                .address(employeeDTO.getAddress()).build();
        Employee savedEmployee = employeeRepository.save(employee);

        if(newEmployeeDTO.getSalaries()!=null && !newEmployeeDTO.getSalaries().isEmpty()){

            for(SalaryDTO salaryDTO:newEmployeeDTO.getSalaries()){
                Salary salary = Salary.builder().salaryCPK(new SalaryCPK(0,savedEmployee.getEmployeeID()))
                        .salaryAmount(salaryDTO.getSalaryAmount()).paymentDate(salaryDTO.getPaymentDate()).build();

                salaryRepository.save(salary);


            }
        }
        return modelMapper.map(savedEmployee,EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO employeeDTO) throws Exception {
        if(employeeDTO == null){
            throw new NullPointerException("Zaposleni ne moze biti null");
        }
        if(employeeDTO.getSalaries().isEmpty()){
            throw new IllegalArgumentException("Zaposleni mora imati odgovore");
        }
        Optional<Employee> dbEmployee = employeeRepository.findById(employeeDTO.getEmployeeID());
        if(dbEmployee.isPresent()) {
            Employee employee = modelMapper.map(employeeDTO,Employee.class);
            Employee savedEmployee = employeeRepository.save(employee);
            if(employeeDTO.getSalaries()!=null && !employeeDTO.getSalaries().isEmpty()){

                for(SalaryDTO salaryDTO:employeeDTO.getSalaries()){
                    Salary salary = modelMapper.map(salaryDTO,Salary.class);

                        salary.setSalaryCPK(new SalaryCPK(salaryDTO.getSalaryCPK().getSalaryID(),employeeDTO.getEmployeeID()));

                    salaryRepository.save(salary);
                }
            }

            return modelMapper.map(savedEmployee, EmployeeDTO.class);
        }
        else{
            throw new NotFoundException("Zaposleni nije pronadjen");
        }
    }

    @Override
    public void deleteById(Object id) throws Exception {

        Optional<Employee> employee = employeeRepository.findById((Integer)id);
        if(!employee.isPresent()){
            throw new NotFoundException("Zaposleni nije pronadjen");
        }
       // Optional<Member> member = memberRepository.findByUsername(student.get().getEmail());
       // Member dbMember = member.get();

        if(hasAssociatedChildren(employee.get())){
            throw new IllegalStateException("Zaposleni ne moze da se izbrise");
        }
        employeeRepository.deleteById((Integer)id);
        //memberRepository.deleteById(dbMember.getId());
    }

    private boolean hasAssociatedChildren(Employee employee) {
        if(requestRepository.findByEmployeeID(employee.getEmployeeID()).isEmpty()) {
            return false;
        }
        else{
            return true;
        }
    }

    public List<EmployeeDTO> getEmployees(Integer id){
        List<Employee> employees = employeeRepository.findByTeamID(id);
        List<EmployeeDTO> employeeDTOS = employees.stream().map(employee->modelMapper.map(employee,EmployeeDTO.class))
                .collect(Collectors.toList());
        return employeeDTOS;
    }

    public List<TimeOffRequestDTO> getRequests(Integer id){
        List<TimeOffRequest> timeOffRequests = requestRepository.findByEmployeeID(id);
        List<TimeOffRequestDTO> timeOffRequestDTOS = timeOffRequests.stream().map(request->modelMapper.map(request,TimeOffRequestDTO.class))
                .collect(Collectors.toList());
        return timeOffRequestDTOS;
    }
    public List<EmploymentProjectAssignmentDTO> getResults(Integer id) throws NotFoundException {
        List<EmploymentProjectAssignment> assignments = assignmentRepository.findByEmployeeID(id);
        if(assignments.isEmpty()){
            return new ArrayList<>();
        }
        List<EmploymentProjectAssignmentDTO> resultsAssignmentDTO = new ArrayList<>();
        for(EmploymentProjectAssignment assignment:assignments){
            EmployeeDTO employeeDTO = modelMapper.map(assignment.getEmployee(),EmployeeDTO.class);
            ProjectDTO projectDTO = modelMapper.map(assignment.getProject(),ProjectDTO.class);


            EmploymentProjectAssignmentDTO assignmentDTO = modelMapper.map(assignment,EmploymentProjectAssignmentDTO.class);
            assignmentDTO.setEmployeeDTO(employeeDTO);
            assignmentDTO.setProjectDTO(projectDTO);

            resultsAssignmentDTO.add(assignmentDTO);
        }
        return resultsAssignmentDTO;
    }
}
