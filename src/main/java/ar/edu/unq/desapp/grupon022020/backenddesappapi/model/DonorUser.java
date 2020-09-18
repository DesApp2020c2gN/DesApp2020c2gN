package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DonorUser extends User {

    private final String nickname;
    private final List<Donation> donations;
    private int points;

    public DonorUser(String name, String nickname, String mail, String password, List<Donation> donations, int points) {
        super(name, mail, password);
        this.nickname = nickname;
        this.donations = donations;
        this.points = points;

    }

    public String getNickname() {
        return nickname;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public int getPoints() {
        return points;
    }

    public void donate(int amount, String comment, Project project) throws InvalidDonationException {
        validateDonation(project);
        Donation donation = DonationBuilder.aDonation().
                withDonorNickname(getNickname()).
                withProjectName(project.getName()).
                withAmount(amount).
                withComment(comment).
                withDate(LocalDate.now()).
                build();
        calculatePoints(amount, project);
        this.donations.add(donation);
        project.receiveDonation(donation);
    }

    private void calculatePoints(int amount, Project project) {
        int currentDonationPoints = 0;
        int population = project.getLocationPopulation();

        if (amount > 1000) {
            currentDonationPoints = amount;
        }
        if (population < 2000) {
            currentDonationPoints = amount * 2;
        }
        LocalDate lastDate = lastDonationDate();
        if (lastDate != null && (LocalDate.now().getMonthValue()) == lastDate.getMonthValue()) {
            currentDonationPoints += 500;
        }
        this.points += currentDonationPoints;
    }

    private LocalDate lastDonationDate() {
        Optional<Donation> lastDonation = donations.stream().max(Comparator.comparing(Donation::getDate));
        if (lastDonation.isPresent()) {
            return lastDonation.get().getDate();
        }
        return null;
    }

    private void validateDonation(Project project) throws InvalidDonationException {
        // This requires the DonorUser to have a variable for money
        if (false) {
            throw new InvalidDonationException("User " + this.getName() + " does not have enough money");
        }
        project.validateDonation();
    }
}
