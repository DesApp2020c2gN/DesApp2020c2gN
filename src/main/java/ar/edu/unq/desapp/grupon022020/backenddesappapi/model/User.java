package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class User {

    private final String name;
    private final String nickname;
    private final String mail;
    private final String password;
    private final List<Donation> donations;
    private int points;

    public User(String name, String nickname, String mail, String password, List<Donation> donations, int points) {
        this.name = name;
        this.nickname = nickname;
        this.mail = mail;
        this.password = password;
        this.donations = donations;
        this.points = points;

    }

    public String getName() {
        return name;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public int getPoints() {
        return points;
    }

    public void donate(int amount, String comment, Project project) {
        Donation donation = DonationBuilder.aDonation().withUser(this).withProject(project)
                .withAmount(amount).withComment(comment).withDate(LocalDate.now()).build();
        this.donations.add(donation);
        calculatePoints(amount, project);
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
        if (lastDonationDate() != null && (Calendar.MONTH) == lastDonationDate().getMonthValue()) {
            currentDonationPoints += 500;
        }
        this.points += currentDonationPoints;
    }

    private LocalDate lastDonationDate() {
        LocalDate lastDonationDate = donations.stream().max(Comparator.comparing(Donation::getDate)).get().getDate();
        return lastDonationDate;
    }
}
