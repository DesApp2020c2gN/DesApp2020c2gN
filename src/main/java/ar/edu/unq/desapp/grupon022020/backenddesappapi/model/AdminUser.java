package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

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
}
