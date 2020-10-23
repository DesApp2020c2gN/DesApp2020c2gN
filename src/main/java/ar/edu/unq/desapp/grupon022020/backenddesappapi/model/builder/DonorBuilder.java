package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DonorBuilder {

    private String name = "default_name";
    private String nickname = "default_nickname";
    private String mail = "default_mail";
    private String password = "default_password";
    private List<Donation> donations = new ArrayList<>();
    private int points = 0;
    private BigDecimal money = new BigDecimal(0);

    public static DonorBuilder aDonorUser() {
        return new DonorBuilder();
    }

    public Donor build() {
        Donor newDonor = new Donor(name, nickname, mail, password, donations, points, money);
        return newDonor;
    }

    public DonorBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DonorBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public DonorBuilder withMail(String mail) {
        this.mail = mail;
        return this;
    }

    public DonorBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public DonorBuilder withDonations(List<Donation> donations) {
        this.donations = donations;
        return this;
    }

    public DonorBuilder withPoints(int points) {
        this.points = points;
        return this;
    }

    public DonorBuilder withMoney(BigDecimal money) {
        this.money = money;
        return this;
    }
}
