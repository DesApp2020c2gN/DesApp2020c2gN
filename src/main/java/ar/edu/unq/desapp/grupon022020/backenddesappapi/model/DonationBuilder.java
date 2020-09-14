package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;

public class DonationBuilder {

    private User user = UserBuilder.aUser().build();
    private Project project = ProjectBuilder.aProject().build();
    private int amount = 1000;
    private String comment = "Default comment";
    private LocalDate date = LocalDate.now();

    public static DonationBuilder aDonation() {
        return new DonationBuilder();
    }

    public Donation build() {
        Donation newDonation = new Donation(user, project, amount, comment, date);
        return newDonation;
    }

    public DonationBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public DonationBuilder withProject(Project project) {
        this.project = project;
        return this;
    }

    public DonationBuilder withAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public DonationBuilder withComment(String comment) {
        this.comment = comment;
        return this;
    }

    public DonationBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }
}
