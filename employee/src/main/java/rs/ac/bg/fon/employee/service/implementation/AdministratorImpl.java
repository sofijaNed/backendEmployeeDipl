package rs.ac.bg.fon.employee.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.employee.dao.AdministratorRepository;
import rs.ac.bg.fon.employee.dto.AdministratorDTO;
import rs.ac.bg.fon.employee.entity.Administrator;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.ServiceInterface;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdministratorImpl implements ServiceInterface<AdministratorDTO> {

    private AdministratorRepository adminRepository;

    private ModelMapper modelMapper;

    @Autowired
    public AdministratorImpl(AdministratorRepository administratorRepository, ModelMapper modelMapper){
        this.adminRepository = administratorRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public List<AdministratorDTO> findAll() {
        return adminRepository.findAll().stream().map(administrator->modelMapper.map(administrator, AdministratorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AdministratorDTO findById(Object id) throws NotFoundException {
        Optional<Administrator> admin = adminRepository.findById((Integer) id);
        AdministratorDTO administratorDTO;
        if(admin.isPresent()){
            administratorDTO = modelMapper.map(admin.get(),AdministratorDTO.class);
        }
        else{
            throw new NotFoundException("Administrator nije pronadjen");
        }
        return administratorDTO;
    }

    @Override
    public AdministratorDTO save(AdministratorDTO administratorDTO) throws Exception {
        if(administratorDTO==null){
            throw new NullPointerException("Administrator ne moze biti null");
        }
        Administrator administrator = adminRepository.save(modelMapper.map(administratorDTO,Administrator.class));
        return modelMapper.map(administrator,AdministratorDTO.class);
    }

    @Override
    public AdministratorDTO update(AdministratorDTO administratorDTO) throws Exception {
        return null;
    }

    @Override
    public void deleteById(Object id) throws Exception {
        if(!adminRepository.findById((Integer)id).isPresent()){
            throw new NotFoundException("Administrator nije pronadjen");
        }
        adminRepository.deleteById((Integer)id);

    }
}
