package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.AdminUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.AdminUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.LocationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Transactional
    public Project save(Project project) {
        return this.projectRepository.save(project);
    }

    public boolean existsById(String name){ return this.projectRepository.existsById(name); }

    public Project findById(String name) { return this.projectRepository.findById(name).get(); }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public Project createProject(String name,
                                 int factor,
                                 int closurePercentage,
                                 String startDate,
                                 int durationInDays,
                                 String locationName) throws InvalidProjectOperationException {
        Project project;
        //TODO: check there is an existing location!
        Optional<Location> location = locationRepository.findById(locationName);
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        project = adminUser.createProject(name, factor, closurePercentage, LocalDate.parse(startDate), durationInDays, location.get());
        save(project);
        return project;
    }

    public boolean existsOpenProject(String locationName) {
        Project project = projectRepository.existsOpenProject(locationName, LocalDate.now());
        return (project != null);
    }
}
