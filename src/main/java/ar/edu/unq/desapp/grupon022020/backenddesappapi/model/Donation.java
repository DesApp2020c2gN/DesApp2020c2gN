package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;
import java.util.Calendar;

public class Donation {

    private User user;
    private Project project;
    private Integer amount;
    private String comment;
    private LocalDate date;

    public Donation(User user, Project project, Integer amount, String comment, LocalDate date) {
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

    public Integer getAmount() {
        return amount;
    }

    public String getComment() {
        return comment;
    }

    public LocalDate getDate() {
        return date;
    }
}
