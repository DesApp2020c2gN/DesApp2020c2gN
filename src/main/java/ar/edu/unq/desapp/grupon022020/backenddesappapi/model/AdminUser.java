package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AdminUser extends User {

    private final System system;

    public AdminUser(String name, String mail, String password, System system) {
        super(name, mail, password);
        this.system = system;
    }

    public List<Location> getLocations() {
        return system.getLocations();
    }

    public List<Project> getOpenProjects() {
        return system.getOpenProjects();
    }

    public List<DonorUser> getUsers() {
        return system.getUsers();
    }

    public void createProject(String name, int factor, int closurePercentage, LocalDate startDate, LocalDate finishDate, Location location) {
        Project project = ProjectBuilder.aProject().
                withName(name).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                withStartDate(startDate).
                withFinishDate(finishDate).
                withLocation(location).
                build();
        this.system.addNewProject(project);
    }

    public void cancelProject(String name) throws InvalidProjectOperation {
        Optional<Project> optionalProject = system.getOpenProject(name);
        if (optionalProject.isPresent()) {
            Project projectToCancel = optionalProject.get();
            system.cancelProject(projectToCancel);
        } else {
            throw new InvalidProjectOperation("Project " + name + " does not exists");
        }
    }

}
