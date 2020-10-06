package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DonationBuilder {

    private DonorUser donor = DonorUserBuilder.aDonorUser().build();
    private String projectName = "default_projectName";
    private BigDecimal amount = new BigDecimal(1000);
    private String comment = "default_comment";
    private LocalDate date = LocalDate.now();

    public static DonationBuilder aDonation() {
        return new DonationBuilder();
    }

    public Donation build() {
        Donation newDonation = new Donation(donor, projectName, amount, comment, date);
        return newDonation;
    }

    public DonationBuilder withDonorNickname(DonorUser donor) {
        this.donor = donor;
        return this;
    }

    public DonationBuilder withProjectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public DonationBuilder withAmount(BigDecimal amount) {
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
