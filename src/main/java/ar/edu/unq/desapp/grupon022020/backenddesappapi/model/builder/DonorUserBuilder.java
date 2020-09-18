package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import java.util.ArrayList;
import java.util.List;

public class DonorUserBuilder {

    private String name = "default_name";
    private String nickname = "default_nickname";
    private String mail = "default_mail";
    private String password = "default_password";
    private List<Donation> donations = new ArrayList<>();
    private int points = 0;

    public static DonorUserBuilder aDonorUser() {
        return new DonorUserBuilder();
    }

    public DonorUser build() {
        DonorUser newDonorUser = new DonorUser(name, nickname, mail, password, donations, points);
        return newDonorUser;
    }

    public DonorUserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DonorUserBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public DonorUserBuilder withMail(String mail) {
        this.mail = mail;
        return this;
    }

    public DonorUserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public DonorUserBuilder withDonations(List<Donation> donations) {
        this.donations = donations;
        return this;
    }

    public DonorUserBuilder withPoints(int points) {
        this.points = points;
        return this;
    }
}
