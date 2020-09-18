package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DonationTest {

    @Test
    public void testDonationDonorNickname() {
        String donorNickname = "Juan2001";
        Donation donation = DonationBuilder.aDonation().withDonorNickname(donorNickname).build();

        assertEquals(donorNickname, donation.getDonorNickname());
    }

    @Test
    public void testDonationProjectName() {
        String projectName = "Conectando San Cristobal";
        Donation donation = DonationBuilder.aDonation().withProjectName(projectName).build();

        assertEquals(projectName, donation.getProjectName());
    }

    @Test
    public void testDonationAmount() {
        int amount = 2530;
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        assertEquals(amount, donation.getAmount());
    }

    @Test
    public void testDonationComment() {
        String comment = "This is a donation";
        Donation donation = DonationBuilder.aDonation().withComment(comment).build();

        assertEquals(comment, donation.getComment());

    }

    @Test
    public void testDonationDate() {
        LocalDate date = LocalDate.now();
        Donation donation = DonationBuilder.aDonation().withDate(date).build();

        assertEquals(date, donation.getDate());
    }

}