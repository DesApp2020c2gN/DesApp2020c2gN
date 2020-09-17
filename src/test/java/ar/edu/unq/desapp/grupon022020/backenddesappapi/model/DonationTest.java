package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class DonationTest {

    @Test
    public void testDonationDonorNickname() {
        String donorNickname = "Juan2001";
        Donation donation = DonationBuilder.aDonation().withDonorNickname(donorNickname).build();

        assertEquals(donation.getDonorNickname(), donorNickname);
    }

    @Test
    public void testDonationProjectName() {
        String projectName = "Conectando San Cristobal";
        Donation donation = DonationBuilder.aDonation().withProjectName(projectName).build();

        assertEquals(donation.getProjectName(), projectName);
    }

    @Test
    public void testDonationAmount() {
        int amount = 2530;
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        assertEquals(donation.getAmount(), amount);
    }

    @Test
    public void testDonationComment() {
        String comment = "This is a donation";
        Donation donation = DonationBuilder.aDonation().withComment(comment).build();

        assertEquals(donation.getComment(), comment);

    }

    @Test
    public void testDonationDate() {
        LocalDate date = LocalDate.now();
        Donation donation = DonationBuilder.aDonation().withDate(date).build();

        assertEquals(donation.getDate(), date);
    }

}