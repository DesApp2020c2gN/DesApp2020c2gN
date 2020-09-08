package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;

public class ProjectBuilder {
    
    
    private String name = "default_name";
    private int factor = 1000;
    private int closurePercentage = 100;
    private LocalDate startDate = LocalDate.now();
    private LocalDate finishDate = LocalDate.now();
    
    public static ProjectBuilder aProject() {
        return new ProjectBuilder();
    }

    public Project build() {
        Project newProject = new Project(name, factor, closurePercentage, startDate, finishDate);
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
}
