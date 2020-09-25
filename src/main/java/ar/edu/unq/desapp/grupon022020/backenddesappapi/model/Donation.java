package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public class Donation {

    private final String donorNickname;
    private final String projectName;
    private final BigDecimal amount;
    private final String comment;
    private final LocalDate date;

    public Donation(String donorNickname, String projectName, BigDecimal amount, String comment, LocalDate date) {
        this.donorNickname = donorNickname;
        this.projectName = projectName;
        this.amount = amount;
        this.comment = comment;
        this.date = date;

    }

    public String getDonorNickname() {
        return donorNickname;
    }

    public String getProjectName() {
        return projectName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }

    public int pointsFromLastDonationOnSameMonth() {
        return 500;
    }

    public int calculatePoints(DonorUser donorUser, Project project) {
        int currentDonationPoints = 0;
        Optional<Donation> donorUserLastDonation = donorUser.getLastDonation();

        if (amount.compareTo(new BigDecimal(1000)) > 0) {
            currentDonationPoints = amount.intValue();
        }
        if (project.getLocationPopulation() < 2000) {
            currentDonationPoints = amount.intValue() * 2;
        }
        LocalDate lastDate = donorUserLastDonation.map(Donation::getDate).orElse(null);
        if (lastDate != null && (LocalDate.now().getMonthValue() == lastDate.getMonthValue()) && (LocalDate.now().getYear() == lastDate.getYear())) {
            currentDonationPoints += pointsFromLastDonationOnSameMonth();
        }
        return currentDonationPoints;
    }

}
