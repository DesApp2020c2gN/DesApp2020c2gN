package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.util.ArrayList;
import java.util.List;

public class UserBuilder {

    private String name = "default_name";
    private String nickname = "default_nickname";
    private String mail = "default_mail";
    private String password = "default_password";
    private List<Donation> donations = new ArrayList<>();

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public User build() {
        User newUser = new User(name, nickname, mail, password, donations);
        return newUser;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public UserBuilder withMail(String mail) {
        this.mail = mail;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withDonations(List<Donation> donations) {
        this.donations = donations;
        return this;
    }
}
