package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.Column;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
public class DonorUser extends User {

    @Id
    private String nickname;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "donorNickname")
    private List<Donation> donations;

    @Column
    private int points;

    @Column
    private BigDecimal money;

    public DonorUser() {};

    public DonorUser(String name, String nickname, String mail, String password, List<Donation> donations, int points, BigDecimal money) {
        super(name, mail, password);
        this.nickname = nickname;
        this.donations = donations;
        this.points = points;
        this.money = money;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<Donation> getDonations() {
        return donations;
    }

    public void setDonations(List<Donation> donations) {
        this.donations = donations;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Donation donate(BigDecimal amount, String comment, Project project) throws InvalidDonationException {
        validateDonation(project, amount);
        Donation donation = DonationBuilder.aDonation().
                withDonorNickname(this.getNickname()).
                withProjectName(project.getName()).
                withAmount(amount).
                withComment(comment).
                withDate(LocalDate.now()).
                build();
        executeDonation(donation, project);
        return donation;
    }

    private void validateDonation(Project project, BigDecimal amount) throws InvalidDonationException {
        if (amount.compareTo(this.money) > 0) {
            throw new InvalidDonationException("User " + this.getName() + " does not have enough money");
        }
        project.validateDonation();
    }

    private void executeDonation(Donation donation, Project project) {
        this.points += donation.calculatePoints(this, project);
        this.donations.add(donation);
        this.money = this.money.subtract(donation.getAmount());
        project.receiveDonation(donation);
    }

    public Optional<Donation> getLastDonation() {
        return donations.stream().max(Comparator.comparing(Donation::getDate));
    }

    public void undoDonation(Donation donation) {
        this.money = this.money.add(donation.getAmount());
        this.donations.remove(donation);
    }
}
