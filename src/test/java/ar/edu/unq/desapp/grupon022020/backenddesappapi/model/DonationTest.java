package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class DonationTest {

    @Test
    public void testDonationUser() {
        User user = mock(User.class);
        Donation donation = DonationBuilder.aDonation().withUser(user).build();

        assertEquals(donation.getUser(), user);
    }

    @Test
    public void testDonationProject() {
        Project project = mock(Project.class);
        Donation donation = DonationBuilder.aDonation().withProject(project).build();

        assertEquals(donation.getProject(), project);
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