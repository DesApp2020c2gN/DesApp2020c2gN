package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.ProjectStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectBuilder {


    private String name = "default_name";
    private int factor = 1000;
    private int closurePercentage = 100;
    private LocalDate startDate = LocalDate.now();
    private int durationInDays = 1;
    private List<Donation> donations = new ArrayList<>();
    private Location location = LocationBuilder.aLocation().build();
    private String status = ProjectStatus.ACTIVE.name();

    public static ProjectBuilder aProject() {
        return new ProjectBuilder();
    }

    public Project build() {
        Project newProject = new Project(name, factor, closurePercentage, startDate, durationInDays, donations, location, status);
        return newProject;
    }

    public ProjectBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProjectBuilder withFactor(int factor) {
        this.factor = factor;
        return this;
    }

    public ProjectBuilder withClosurePercentage(int closurePercentage) {
        this.closurePercentage = closurePercentage;
        return this;
    }

    public ProjectBuilder withStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public ProjectBuilder withDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
        return this;
    }

    public ProjectBuilder withDonations(List<Donation> donations) {
        this.donations = donations;
        return this;
    }

    public ProjectBuilder withLocation(Location location) {
        this.location = location;
        return this;
    }

    public ProjectBuilder withStatus(String status) {
        this.status = status;
        return this;
    }
}
