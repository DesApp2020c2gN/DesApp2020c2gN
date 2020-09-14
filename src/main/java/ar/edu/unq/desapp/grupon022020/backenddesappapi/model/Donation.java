package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;

public class Donation {

    private final User user;
    private final Project project;
    private final int amount;
    private final String comment;
    private final LocalDate date;

    public Donation(User user, Project project, int amount, String comment, LocalDate date) {
        this.user = user;
        this.project = project;
        this.amount = amount;
        this.comment = comment;
        this.date = date;

    }

    public User getUser() {
        return user;
    }

    public Project getProject() {
        return project;
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
