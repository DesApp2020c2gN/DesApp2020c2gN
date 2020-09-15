package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;
import java.util.List;

public class Project {

    private final List<Donation> donations;
    private final String name;
    private final int factor;
    private final int closurePercentage;
    private final LocalDate startDate;
    private final LocalDate finishDate;
    private final Location location;

    public Project(String name, int factor, int closurePercentage, LocalDate startDate, LocalDate finishDate, List<Donation> donations, Location location) {
        this.name = name;
        this.factor = factor;
        this.closurePercentage = closurePercentage;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.donations = donations;
        this.location = location;
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

    public List<Donation> getDonations() {
        return donations;
    }

    public Location getLocation() {
        return location;
    }

    public void receiveDonation(Donation donation) {
        this.donations.add(donation);
    }

    public int getLocationPopulation() {
        return this.location.getPopulation();
    }
}
