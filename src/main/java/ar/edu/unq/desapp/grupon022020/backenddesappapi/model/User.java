package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.util.List;

public class User {

    private final String name;
    private final String nickname;
    private final String mail;
    private final String password;
    private final List<Donation> donations;

    public User(String name, String nickname, String mail, String password, List<Donation> donations) {
        this.name = name;
        this.nickname = nickname;
        this.mail = mail;
        this.password = password;
        this.donations = donations;

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
}