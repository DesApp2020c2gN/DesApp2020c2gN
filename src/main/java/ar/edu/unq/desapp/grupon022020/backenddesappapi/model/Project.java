package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
//@SequenceGenerator(name = "SEQ_PROJECT", sequenceName = "SEQUENCE_PROJECT", initialValue = 1, allocationSize = 1)
public class Project {

    //@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROJECT")
    //private Integer id;

    @Id
    private String name;

    @Column
    private int factor;

    @Column
    private int closurePercentage;

    @Column
    private LocalDate startDate;

    @Column
    private LocalDate finishDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "project")
    private List<Donation> donations;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    //@JoinColumn(name = "locationId", referencedColumnName = "id")
    private Location location;

    public Project() {}

    public Project(String name, int factor, int closurePercentage, LocalDate startDate, int durationInDays, List<Donation> donations, Location location) {
        this.name = name;
        this.factor = factor;
        this.closurePercentage = closurePercentage;
        this.startDate = startDate;
        this.finishDate = startDate.plusDays(durationInDays);
        this.donations = donations;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFactor() {
        return factor;
    }

    public void setFactor(int factor) {
        this.factor = factor;
    }

    public int getClosurePercentage() {
        return closurePercentage;
    }

    public void setClosurePercentage(int closurePercentage) {
        this.closurePercentage = closurePercentage;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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
        return percentageAchieved() >= this.getClosurePercentage();
    }

    public float percentageAchieved(){
        return ((float) totalAmountDonations().intValue() / moneyRequired()) * 100;
    }

    public BigDecimal totalAmountDonations() {
        return new BigDecimal(donations.stream().map(Donation::getAmount).map(BigDecimal::intValue).reduce(0, Integer::sum));
    }

    public int moneyRequired() {
        return this.factor * this.getLocation().getPopulation();
    }

    public void cancel() {
        this.finishDate = getStartDate().minusDays(1);
    }

    public void undoDonations() {
        this.donations.clear();
    }

    public int numberOfDonors(){
        return ((int) this.donations.stream().map(Donation::getDonorUser).distinct().count());
    }
    
    public Optional<Donation> getLastDonation() {
        return donations.stream().max(Comparator.comparing(Donation::getDate));
    }
}
