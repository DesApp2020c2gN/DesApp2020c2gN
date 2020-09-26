package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AdminUser extends User {

    private final Manager manager;

    public AdminUser(String name, String mail, String password, Manager manager) {
        super(name, mail, password);
        this.manager = manager;
    }

    public List<Location> getLocations() {
        return manager.getLocations();
    }

    public List<Project> getOpenProjects() {
        return manager.getOpenProjects();
    }

    public List<DonorUser> getUsers() {
        return manager.getUsers();
    }

    public Project createProject(String name, int factor, int closurePercentage, LocalDate startDate, int durationInDays, Location location) throws InvalidProjectOperation {
        if (startDate.isBefore(LocalDate.now())) {
            throw new InvalidProjectOperation("Start day of " + startDate.toString() + " for project " + name + " is not valid");
        }
        Project project = ProjectBuilder.aProject().
                withName(name).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                withStartDate(startDate).
                withDurationInDays(durationInDays).
                withLocation(location).
                build();
        this.manager.addNewProject(project);
        return project;
    }

    public void cancelProject(String name) throws InvalidProjectOperation {
        Optional<Project> optionalProject = manager.getOpenProject(name);
        if (optionalProject.isPresent()) {
            Project projectToCancel = optionalProject.get();
            manager.cancelProject(projectToCancel);
        } else {
            throw new InvalidProjectOperation("Project " + name + " does not exists");
        }
    }

}
