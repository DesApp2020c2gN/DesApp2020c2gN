package ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;

import java.math.BigDecimal;
import java.time.LocalDate;

public class DonationBuilder {

    private DonorUser donorUser = DonorUserBuilder.aDonorUser().build();
    private Project project = ProjectBuilder.aProject().build();
    private BigDecimal amount = new BigDecimal(1000);
    private String comment = "default_comment";
    private LocalDate date = LocalDate.now();

    public static DonationBuilder aDonation() {
        return new DonationBuilder();
    }

    public Donation build() {
        Donation newDonation = new Donation(donorUser, project, amount, comment, date);
        return newDonation;
    }

    public DonationBuilder withDonorUser(DonorUser donorUser) {
        this.donorUser = donorUser;
        return this;
    }

    public DonationBuilder withProject(Project project) {
        this.project = project;
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
