package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.AdminUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;

import java.util.ArrayList;
import java.util.List;

public class AdminUserBuilder {

    private String name = "default_name";
    private String mail = "default_mail";
    private String password = "default_password";
    private List<Location> locations = new ArrayList<>();
    private List<Project> projects = new ArrayList<>();

    public static AdminUserBuilder anAdminUser() {
        return new AdminUserBuilder();
    }

    public AdminUser build() {
        AdminUser newAdminUser = new AdminUser(name, mail, password, locations, projects);
        return newAdminUser;
    }

    public AdminUserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public AdminUserBuilder withMail(String mail) {
        this.mail = mail;
        return this;
    }

    public AdminUserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public AdminUserBuilder withLocations(List<Location> locations) {
        this.locations = locations;
        return this;
    }

    public AdminUserBuilder withProjects(List<Project> projects) {
        this.projects = projects;
        return this;
    }
}
