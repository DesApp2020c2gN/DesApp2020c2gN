package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;

import java.time.LocalDate;
import java.util.List;

public class Project {

    private final String name;
    private final int factor;
    private final int closurePercentage;
    private final LocalDate startDate;
    private final LocalDate finishDate;
    private final List<Donation> donations;
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

    public void validateDonation() throws InvalidDonationException {
        LocalDate today = LocalDate.now();
        if (today.isBefore(this.getStartDate())) {
            throw new InvalidDonationException("Project " + this.getName() + " has not started");
        }
        if (today.isAfter(this.getFinishDate())) {
            throw new InvalidDonationException("Project " + this.getName() + " has finished");
        }
    }

    public boolean hasReachedGoal() {
        float percentageAchieved = ((float) totalDonations() / moneyRequired()) * 100;
        return percentageAchieved > this.getClosurePercentage();
    }

    private int totalDonations() {
        return donations.stream().map(Donation::getAmount).reduce(0, Integer::sum);
    }

    private int moneyRequired() {
        return this.factor * this.getLocation().getPopulation();
    }

}
