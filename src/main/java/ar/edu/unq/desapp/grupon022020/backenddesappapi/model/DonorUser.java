package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.ProjectClosedException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DonorUser {

    private final String name;
    private final String nickname;
    private final String mail;
    private final String password;
    private final List<Donation> donations;
    private int points;

    public DonorUser(String name, String nickname, String mail, String password, List<Donation> donations, int points) {
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

    public void donate(int amount, String comment, Project project) throws ProjectClosedException {
        validateProjectForDonation(project);
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

    private void validateProjectForDonation(Project project) throws ProjectClosedException {
        LocalDate today = LocalDate.now();
        if (today.isAfter(project.getFinishDate())) {
            throw new ProjectClosedException();
        }
    }
}
