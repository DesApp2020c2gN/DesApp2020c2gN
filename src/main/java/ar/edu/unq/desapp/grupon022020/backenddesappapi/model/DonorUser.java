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
    private int money;

    public DonorUser(String name, String nickname, String mail, String password, List<Donation> donations, int points, int money) {
        super(name, mail, password);
        this.nickname = nickname;
        this.donations = donations;
        this.points = points;
        this.money = money;
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

    public int getMoney() {
        return money;
    }

    public void donate(int amount, String comment, Project project) throws InvalidDonationException {
        validateDonation(project, amount);
        Donation donation = DonationBuilder.aDonation().
                withDonorNickname(getNickname()).
                withProjectName(project.getName()).
                withAmount(amount).
                withComment(comment).
                withDate(LocalDate.now()).
                build();
        executeDonation(donation, project);
    }

    private void validateDonation(Project project, int amount) throws InvalidDonationException {
        if (amount > this.money) {
            throw new InvalidDonationException("User " + this.getName() + " does not have enough money");
        }
        project.validateDonation();
    }

    private void executeDonation(Donation donation, Project project) {
        calculatePoints(donation.getAmount(), project);
        this.donations.add(donation);
        this.money -= donation.getAmount();
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
        if (lastDate != null && (LocalDate.now().getMonthValue() == lastDate.getMonthValue()) && (LocalDate.now().getYear() == lastDate.getYear())) {
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

    public void undoDonation(Donation donation) {
        this.money += donation.getAmount();
        this.donations.remove(donation);
    }
}
