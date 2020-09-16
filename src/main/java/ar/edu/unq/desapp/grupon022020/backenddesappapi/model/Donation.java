package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;

public class Donation {

    private final String userNickname;
    private final String projectName;
    private final int amount;
    private final String comment;
    private final LocalDate date;

    public Donation(String userNickname, String projectName, int amount, String comment, LocalDate date) {
        this.userNickname = userNickname;
        this.projectName = projectName;
        this.amount = amount;
        this.comment = comment;
        this.date = date;

    }

    public String getUserNickname() {
        return userNickname;
    }

    public String getProjectName() {
        return projectName;
    }

    public int getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }
}
