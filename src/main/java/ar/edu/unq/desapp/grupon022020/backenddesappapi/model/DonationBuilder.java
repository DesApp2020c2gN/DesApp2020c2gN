package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;

public class DonationBuilder {

    private String donorNickname = "default_donorNickname";
    private String projectName = "default_projectName";
    private int amount = 1000;
    private String comment = "default_comment";
    private LocalDate date = LocalDate.now();

    public static DonationBuilder aDonation() {
        return new DonationBuilder();
    }

    public Donation build() {
        Donation newDonation = new Donation(donorNickname, projectName, amount, comment, date);
        return newDonation;
    }

    public DonationBuilder withDonorNickname(String donorNickname) {
        this.donorNickname = donorNickname;
        return this;
    }

    public DonationBuilder withProjectName(String projectName) {
        this.projectName = projectName;
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
