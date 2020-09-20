package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.System;

import java.util.ArrayList;
import java.util.List;

public class SystemBuilder {

    private List<Project> openProjects = new ArrayList<>();
    private List<Project> closedProjects = new ArrayList<>();
    private List<DonorUser> donorUsers = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();

    public static SystemBuilder aSystem() {
        return new SystemBuilder();
    }

    public System build() {
        System newSystem = new System(openProjects, closedProjects, donorUsers, locations);
        return newSystem;
    }

    public SystemBuilder withOpenProjects(List<Project> openProjects) {
        this.openProjects = openProjects;
        return this;
    }

    public SystemBuilder withClosedProjects(List<Project> closedProjects) {
        this.closedProjects = closedProjects;
        return this;
    }

    public SystemBuilder withUsers(List<DonorUser> donorUsers) {
        this.donorUsers = donorUsers;
        return this;
    }

    public SystemBuilder withLocations(List<Location> locations) {
        this.locations = locations;
        return this;
    }
}
