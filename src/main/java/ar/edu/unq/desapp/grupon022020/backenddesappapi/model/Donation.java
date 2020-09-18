package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;

public class Donation {

    private final String donorNickname;
    private final String projectName;
    private final int amount;
    private final String comment;
    private final LocalDate date;

    public Donation(String donorNickname, String projectName, int amount, String comment, LocalDate date) {
        this.donorNickname = donorNickname;
        this.projectName = projectName;
        this.amount = amount;
        this.comment = comment;
        this.date = date;

    }

    public String getDonorNickname() {
        return donorNickname;
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
