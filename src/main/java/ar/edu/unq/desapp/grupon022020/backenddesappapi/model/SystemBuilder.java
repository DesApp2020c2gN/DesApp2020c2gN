package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.util.ArrayList;
import java.util.List;

public class SystemBuilder {

    private List<Project> projects = new ArrayList<>();
    private List<DonorUser> donorUsers = new ArrayList<>();

    public static SystemBuilder aSystem() {
        return new SystemBuilder();
    }

    public System build() {
        System newSystem = new System(projects, donorUsers);
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
}
