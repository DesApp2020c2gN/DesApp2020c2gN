package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectBuilder {


    private String name = "default_name";
    private int factor = 1000;
    private int closurePercentage = 100;
    private LocalDate startDate = LocalDate.now();
    private LocalDate finishDate = LocalDate.now();
    private List<Donation> donations = new ArrayList<>();
    private Location location = LocationBuilder.aLocation().build();

    public static ProjectBuilder aProject() {
        return new ProjectBuilder();
    }

    public Project build() {
        Project newProject = new Project(name, factor, closurePercentage, startDate, finishDate, donations, location);
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

    public ProjectBuilder withFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
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
}
