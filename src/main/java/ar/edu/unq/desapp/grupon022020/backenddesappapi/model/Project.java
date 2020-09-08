package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;
        
public class Project {
    
    private String name;
    private int factor;
    private int closurePercentage;
    private LocalDate startDate;
    private LocalDate finishDate;

    public Project(String name, int factor, int closurePercentage, LocalDate startDate, LocalDate finishDate) {
        this.name = name;
        this.factor = factor;
        this.closurePercentage = closurePercentage;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public String getName() {
        return name;
    }

    public int getFactor() {
        return factor;
    }

    public int getClosurePercentage() {
        return closurePercentage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }
    
}
