package rs.ac.bg.fon.employee.service.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.bg.fon.employee.dao.EmploymentProjectAssignmentRepository;
import rs.ac.bg.fon.employee.dao.ProjectRepository;
import rs.ac.bg.fon.employee.dto.*;
import rs.ac.bg.fon.employee.entity.Employee;
import rs.ac.bg.fon.employee.entity.EmploymentProjectAssignment;
import rs.ac.bg.fon.employee.entity.Project;
import rs.ac.bg.fon.employee.entity.complexprimarykey.EmploymentProjectAssignmentCPK;
import rs.ac.bg.fon.employee.exception.type.NotFoundException;
import rs.ac.bg.fon.employee.service.ServiceInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectImpl implements ServiceInterface<ProjectDTO> {

    private ProjectRepository projectRepository;

    private EmploymentProjectAssignmentRepository assignmentRepository;

    private ModelMapper modelMapper;

    @Autowired
    public ProjectImpl(ProjectRepository projectRepository, EmploymentProjectAssignmentRepository assignmentRepository, ModelMapper modelMapper) {
        this.projectRepository = projectRepository;
        this.assignmentRepository = assignmentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProjectDTO> findAll() {

        List<Project> projects = projectRepository.findAll();
        List<ProjectDTO> projectDTOS = new ArrayList<>();
        for(Project project:projects){
            TeamDTO teamDTO = modelMapper.map(project.getTeam(),TeamDTO.class);
            ProjectDTO projectDTO = modelMapper.map(project,ProjectDTO.class);
            projectDTO.setTeamDTO(teamDTO);
            projectDTOS.add(projectDTO);
        }
        return projectDTOS;
    }

    @Override
    public ProjectDTO findById(Object id) throws NotFoundException {
        Optional<Project> project = projectRepository.findById((Integer) id);
        ProjectDTO projectDTO;
        if(project.isPresent()){
            projectDTO = modelMapper.map(project.get(),ProjectDTO.class);

            Collection<EmploymentProjectAssignmentDTO> results = getResults((Integer) id);
            projectDTO.setAssignments(results);
        }
        else{
            throw new NotFoundException("Projekat nije pronadjen");
        }
        return projectDTO;
    }

    @Override
    public ProjectDTO save(ProjectDTO projectDTO) throws Exception {
        if(projectDTO == null){
            throw new NullPointerException("Projekat ne moze biti null");
        }
        if(projectDTO.getAssignments().isEmpty()){
            throw new IllegalArgumentException("Projekat mora imati platu.");
        }


        Project project = Project.builder().projectName(projectDTO.getProjectName())
                .description(projectDTO.getDescription())
                .startDate(projectDTO.getStartDate())
                .endDate(projectDTO.getEndDate()).build();
        Project savedProject = projectRepository.save(project);

        if(projectDTO.getAssignments()!=null && !projectDTO.getAssignments().isEmpty()){

            for(EmploymentProjectAssignmentDTO assignmentDTO:projectDTO.getAssignments()){
                EmploymentProjectAssignment assignment = EmploymentProjectAssignment.builder().
                        assignmentCPK(new EmploymentProjectAssignmentCPK(0,assignmentDTO.getAssignmentCPK().getEmployeeID(),
                                savedProject.getProjectID()))
                        .description(assignmentDTO.getDescription())
                        .status(assignmentDTO.getStatus()).build();

                assignmentRepository.save(assignment);


            }
        }
        return modelMapper.map(savedProject,ProjectDTO.class);
    }

    @Override
    public ProjectDTO update(ProjectDTO projectDTO) throws Exception {
        if(projectDTO == null){
            throw new NullPointerException("Projekat ne moze biti null");
        }
        if(projectDTO.getAssignments().isEmpty()){
            throw new IllegalArgumentException("Projekat mora imati odgovore");
        }
        Optional<Project> dbProject = projectRepository.findById(projectDTO.getProjectID());
        if(dbProject.isPresent()) {
            Project project = modelMapper.map(projectDTO,Project.class);
            Project savedProject = projectRepository.save(project);
            if(projectDTO.getAssignments()!=null && !projectDTO.getAssignments().isEmpty()){

                for(EmploymentProjectAssignmentDTO assignmentDTO:projectDTO.getAssignments()){
                    EmploymentProjectAssignment assignment = modelMapper.map(assignmentDTO,EmploymentProjectAssignment.class);

                    assignment.setAssignmentCPK(new EmploymentProjectAssignmentCPK(assignmentDTO.getAssignmentCPK().getAssignmentID(), assignmentDTO.getEmployeeDTO().getEmployeeID(),
                            savedProject.getProjectID()));

                    assignmentRepository.save(assignment);
                }
            }

            return modelMapper.map(savedProject, ProjectDTO.class);
        }
        else{
            throw new NotFoundException("Projekat nije pronadjen");
        }
    }

    @Override
    public void deleteById(Object id) throws Exception {

        if(!projectRepository.findById((Integer)id).isPresent()){
            throw new NotFoundException("Projekat nije pronadjen");
        }
        projectRepository.deleteById((Integer) id);
    }

    public List<EmploymentProjectAssignmentDTO> getResults(Integer id) throws NotFoundException {
        List<EmploymentProjectAssignment> assignments = assignmentRepository.findByProjectID(id);
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

    public List<ProjectDTO> getProjects(Integer id){
        List<Project> projects = projectRepository.findByTeamID(id);
        List<ProjectDTO> projectDTOS = projects.stream().map(project->modelMapper.map(project,ProjectDTO.class))
                .collect(Collectors.toList());
        return projectDTOS;
    }

    public EmploymentProjectAssignmentDTO saveAssignments(EmploymentProjectAssignmentDTO assignmentDTO) {
        if(assignmentDTO == null){
            throw new NullPointerException("Zadaci ne mogu biti null");
        }
        EmploymentProjectAssignment assignment = assignmentRepository.save(modelMapper.map(assignmentDTO,EmploymentProjectAssignment.class));
        return modelMapper.map(assignment,EmploymentProjectAssignmentDTO.class);
    }


    public void deleteResultExam(Integer projectID, Integer employeeID) throws NotFoundException {
        if(!assignmentRepository.findById(new EmploymentProjectAssignmentCPK(0,projectID,employeeID)).isPresent()){
            throw new NotFoundException("Zadatak nije pronadjen");
        }
        assignmentRepository.deleteById(new EmploymentProjectAssignmentCPK(0, projectID,employeeID));
    }

    public List<EmployeeDTO> getEmployees(Integer employeeID) throws NotFoundException  {
        List<EmploymentProjectAssignment> assignments = assignmentRepository.findByProjectID(employeeID);
        if(assignments.isEmpty()){
            throw new NotFoundException("Zaposleni sa id-em: " + employeeID + " nisu pronadjeni");
        }

        List<EmployeeDTO> employeeDTOS = new ArrayList<>();
        for(EmploymentProjectAssignment assignment : assignments){
            EmployeeDTO employeeDTO = modelMapper.map(assignment.getEmployee(),EmployeeDTO.class);
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }
}
