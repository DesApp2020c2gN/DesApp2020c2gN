package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.System;

import java.util.ArrayList;
import java.util.List;

public class SystemBuilder {

    private List<Project> projects = new ArrayList<>();
    private List<DonorUser> donorUsers = new ArrayList<>();
    private List<Location> locations = new ArrayList<>();

    public static SystemBuilder aSystem() {
        return new SystemBuilder();
    }

    public System build() {
        System newSystem = new System(projects, donorUsers, locations);
        return newSystem;
    }

    public SystemBuilder withProjects(List<Project> projects) {
        this.projects = projects;
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
