package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.util.ArrayList;
import java.util.List;

public class SystemBuilder {

    private List<Project> projects = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    public static SystemBuilder aSystem() {
        return new SystemBuilder();
    }

    public System build() {
        System newSystem = new System(projects, users);
        return newSystem;
    }

    public SystemBuilder withProjects(List<Project> projects) {
        this.projects = projects;
        return this;
    }

    public SystemBuilder withUsers(List<User> users) {
        this.users = users;
        return this;
    }
}
