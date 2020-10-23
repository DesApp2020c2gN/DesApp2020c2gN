package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Entity
public class Donor {

    @Id
    @NotBlank(message = "Nickname cannot be blank")
    private String nickname;

    @Column
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column
    @Email
    @NotBlank(message = "Mail cannot be blank")
    private String mail;

    @Column
    @NotBlank(message = "Password cannot be blank")
    private String password;
    //TODO: save password as Hash in the database!

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "donorNickname")
    private List<Donation> donations;

    @Column
    private int points;

    @Column
    @PositiveOrZero(message = "Money should be zero or positive")
    private BigDecimal money;

    public Donor() {};

    public Donor(String name, String nickname, String mail, String password, List<Donation> donations, int points, BigDecimal money) {
        this.nickname = nickname;
        this.name = name;
        this.mail = mail;
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
            throw new InvalidDonationException("User " + this.getNickname() + " does not have enough money");
        }
        project.validateDonation();
    }

    private void executeDonation(Donation donation, Project project) {
        this.points += donation.calculatePoints(this, project);
        this.donations.add(donation);
        this.money = this.money.subtract(donation.getAmount());
        project.receiveDonation(donation);
    }

    public Optional<Donation> lastDonation() {
        return donations.stream().max(Comparator.comparing(Donation::getDate));
    }

    public void undoDonation(Donation donation) {
        //TODO: return points when donation is cancelled!
        this.money = this.money.add(donation.getAmount());
        this.donations.remove(donation);
    }
}
