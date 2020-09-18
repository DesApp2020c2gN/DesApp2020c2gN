package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;

import java.time.LocalDate;
import java.util.List;

public class AdminUser extends User {

    private final List<Location> locations;
    private final List<Project> projects;

    public AdminUser(String name, String mail, String password, List<Location> locations, List<Project> projects) {
        super(name, mail, password);
        this.locations = locations;
        this.projects = projects;

    }

    public List<Location> getLocations() {
        return locations;
    }

    public List<Project> getProjects() {
        return projects;
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
        this.projects.add(project);
    }
}
