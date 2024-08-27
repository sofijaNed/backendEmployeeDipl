package rs.ac.bg.fon.employee.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.employee.dao.EmployeeRepository;
import rs.ac.bg.fon.employee.dao.TimeOffRequestRepository;
import rs.ac.bg.fon.employee.dto.EmployeeDTO;
import rs.ac.bg.fon.employee.dto.TimeOffRequestDTO;
import rs.ac.bg.fon.employee.entity.TimeOffRequest;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.ServiceInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeOffRequestImpl implements ServiceInterface<TimeOffRequestDTO> {
    private TimeOffRequestRepository requestRepository;

    private EmployeeRepository employeeRepository;

    private ModelMapper modelMapper;

    @Autowired
    public TimeOffRequestImpl(TimeOffRequestRepository requestRepository, EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.requestRepository = requestRepository;
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TimeOffRequestDTO> findAll() {
        List<TimeOffRequest> timeOffRequests = requestRepository.findAll();
        List<TimeOffRequestDTO> timeOffRequestDTOS = new ArrayList<>();
        for(TimeOffRequest timeOffRequest:timeOffRequests){
            EmployeeDTO employeeDTO = modelMapper.map(timeOffRequest.getEmployee(),EmployeeDTO.class);
            TimeOffRequestDTO timeOffRequestDTO = modelMapper.map(timeOffRequest,TimeOffRequestDTO.class);
            timeOffRequestDTO.setEmployeeDTO(employeeDTO);
            timeOffRequestDTOS.add(timeOffRequestDTO);
        }
        return timeOffRequestDTOS;
    }

    @Override
    public TimeOffRequestDTO findById(Object id) throws NotFoundException {

        Optional<TimeOffRequest> timeOffRequest = requestRepository.findById((Integer)id);
        TimeOffRequestDTO timeOffRequestDTO;
        if(timeOffRequest.isPresent()){
            timeOffRequestDTO = modelMapper.map(timeOffRequest.get(),TimeOffRequestDTO.class);
        }
        else{
            throw new NotFoundException("Zahtev za odmor nije pronadjen");
        }
        return timeOffRequestDTO;
    }

    @Override
    public TimeOffRequestDTO save(TimeOffRequestDTO timeOffRequestDTO) throws Exception {
        if (timeOffRequestDTO == null) {
            throw new NullPointerException("Zahtev za odmor ne moze biti null");
        }
        TimeOffRequest timeOffRequest = modelMapper.map(timeOffRequestDTO,TimeOffRequest.class);
        TimeOffRequest savedRequest = requestRepository.save(timeOffRequest);
        return modelMapper.map(savedRequest, TimeOffRequestDTO.class);
    }

    @Override
    public TimeOffRequestDTO update(TimeOffRequestDTO timeOffRequestDTO) throws Exception {
        if(timeOffRequestDTO==null){
            throw new NullPointerException("Zahtev za odmor ne moze biti null");
        }
        Optional<TimeOffRequest> dbRequest = requestRepository.findById(timeOffRequestDTO.getRequestID());
        if(dbRequest.isPresent()) {
            TimeOffRequest request = modelMapper.map(timeOffRequestDTO,TimeOffRequest.class);
            TimeOffRequest savedRequest = requestRepository.save(request);
            return modelMapper.map(savedRequest, TimeOffRequestDTO.class);
        }
        else{
            throw new NotFoundException("Zahtev za odmor nije pronadjen");
        }
    }

    @Override
    public void deleteById(Object id) throws Exception {


        if(!requestRepository.findById((Integer)id).isPresent()){
            throw new NotFoundException("Zahtev za odmor nije pronadjen");
        }
        requestRepository.deleteById((Integer) id);
    }

    public List<TimeOffRequestDTO> getRequests(Integer id){
        List<TimeOffRequest> requests = requestRepository.findByEmployeeID((Integer) id);
        List<TimeOffRequestDTO> timeOffRequestDTOS = requests.stream().map(request->modelMapper.map(request,TimeOffRequestDTO.class))
                .collect(Collectors.toList());
        return timeOffRequestDTOS;
    }


}
