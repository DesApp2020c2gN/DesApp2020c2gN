package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Manager;

import java.util.ArrayList;
import java.util.List;

public class ManagerBuilder {

    private List<Project> openProjects = new ArrayList<>();
    private List<Project> closedProjects = new ArrayList<>();
    private List<DonorUser> donorUsers = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();

    public static ManagerBuilder aManager() {
        return new ManagerBuilder();
    }

    public Manager build() {
        Manager newManager = new Manager(openProjects, closedProjects, donorUsers, locations);
        return newManager;
    }

    public ManagerBuilder withOpenProjects(List<Project> openProjects) {
        this.openProjects = openProjects;
        return this;
    }

    public ManagerBuilder withClosedProjects(List<Project> closedProjects) {
        this.closedProjects = closedProjects;
        return this;
    }

    public ManagerBuilder withUsers(List<DonorUser> donorUsers) {
        this.donorUsers = donorUsers;
        return this;
    }

    public ManagerBuilder withLocations(List<Location> locations) {
        this.locations = locations;
        return this;
    }
}
