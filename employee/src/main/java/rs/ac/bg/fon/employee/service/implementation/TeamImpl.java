package rs.ac.bg.fon.employee.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.employee.dao.EmployeeRepository;
import rs.ac.bg.fon.employee.dao.ProjectRepository;
import rs.ac.bg.fon.employee.dao.TeamRepository;

import rs.ac.bg.fon.employee.dto.EmployeeDTO;
import rs.ac.bg.fon.employee.dto.TeamDTO;

import rs.ac.bg.fon.employee.entity.Team;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.ServiceInterface;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamImpl implements ServiceInterface<TeamDTO> {

    private TeamRepository teamRepository;

    private EmployeeRepository employeeRepository;
    private EmployeeImpl employeeIml;

    private ProjectRepository projectRepository;

    private ModelMapper modelMapper;


    @Autowired
    public TeamImpl(TeamRepository teamRepository, EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<TeamDTO> findAll() {
        return teamRepository.findAll().stream().map(team->modelMapper.map(team,TeamDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public TeamDTO findById(Object id) throws NotFoundException {
        Optional<Team> team = teamRepository.findById((Integer) id);
        TeamDTO teamDTO;
        if(team.isPresent()){
            teamDTO = modelMapper.map(team.get(),TeamDTO.class);
            Collection<EmployeeDTO> employeesDTO = employeeIml.getEmployees((Integer) id);
            teamDTO.setEmployees(employeesDTO);
        }
        else{
            throw new NotFoundException("Tim nije pronadjen");
        }
        return teamDTO;
    }

    @Override
    public TeamDTO save(TeamDTO teamDTO) throws Exception {
        if (teamDTO == null) {
            throw new NullPointerException("Tim ne moze biti null");
        }
        Team team = modelMapper.map(teamDTO,Team.class);
        Team savedTeam = teamRepository.save(team);
        return modelMapper.map(savedTeam, TeamDTO.class);
    }

    @Override
    public TeamDTO update(TeamDTO teamDTO) throws Exception {
        if(teamDTO==null){
            throw new NullPointerException("Tim ne moze biti null");
        }
        Optional<Team> dbTeam = teamRepository.findById(teamDTO.getTeamID());
        if(dbTeam.isPresent()) {
            Team team = modelMapper.map(teamDTO,Team.class);
            Team savedTeam = teamRepository.save(team);
            return modelMapper.map(savedTeam, TeamDTO.class);
        }
        else{
            throw new NotFoundException("Tim nije pronadjen");
        }
    }

    @Override
    public void deleteById(Object id) throws Exception {
        Optional<Team> team = teamRepository.findById((Integer) id);
        if(!teamRepository.findById((Integer)id).isPresent()){
            throw new NotFoundException("Tim nije pronadjen");
        }
        if(hasAssociatedChildren(team.get())){
            throw new IllegalStateException("Tim ne moze da se izbrise");
        }
        teamRepository.deleteById((Integer)id);

    }

    private boolean hasAssociatedChildren(Team team) {
        if(employeeRepository.findByTeamID(team.getTeamID()).isEmpty() && projectRepository.findByTeamID(team.getTeamID()).isEmpty()) {
            return false;
        }
        else{
            return true;
        }
    }
}
