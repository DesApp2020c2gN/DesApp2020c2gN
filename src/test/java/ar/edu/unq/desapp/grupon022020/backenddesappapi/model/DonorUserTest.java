package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DonorUserTest {

    @Test
    public void testDonorUserName() {
        String name = "Marcelo";
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withName(name).build();

        assertEquals(donorUser.getName(), name);
    }

    @Test
    public void testDonorUserNickname() {
        String nickname = "Marce98";
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withNickname(nickname).build();

        assertEquals(donorUser.getNickname(), nickname);
    }

    @Test
    public void testDonorUserMail() {
        String mail = "marce98@gmail.com";
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMail(mail).build();

        assertEquals(donorUser.getMail(), mail);
    }

    @Test
    public void testDonorUserPassword() {
        String password = "marce_98_1001";
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withPassword(password).build();

        assertEquals(donorUser.getPassword(), password);
    }

    @Test
    public void testDonorUserDonationList() {
        List<Donation> donations = new ArrayList<>();
        Donation donation_1 = mock(Donation.class);
        Donation donation_2 = mock(Donation.class);
        donations.add(donation_1);
        donations.add(donation_2);

        DonorUser donorUser = DonorUserBuilder.aDonorUser().withDonations(donations).build();

        assertEquals(donorUser.getDonations(), donations);
    }

    @Test
    public void testDonorUserPoints() {
        int points = 71;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withPoints(points).build();

        assertEquals(donorUser.getPoints(), points);
    }

    @Test
    public void testDonorUserDonation() {
        DonorUser donorUser = DonorUserBuilder.aDonorUser().build();
        assertTrue(donorUser.getDonations().isEmpty());

        int amount = 3570;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        donorUser.donate(amount, comment, project);
        assertFalse(donorUser.getDonations().isEmpty());

        Donation donation = donorUser.getDonations().get(0);
        assertEquals(donation.getAmount(), amount);
        assertEquals(donation.getComment(), comment);
    }

    @Test
    public void testDonorUserPointsWithLess1000AmountAndPlus2000Population() {
        DonorUser donorUser = DonorUserBuilder.aDonorUser().build();
        assertEquals(donorUser.getPoints(), 0);

        int donationAmount = 500;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        donorUser.donate(donationAmount, comment, project);

        assertEquals(donorUser.getPoints(), 0);
    }

    @Test
    public void testDonorUserPointsWithPlus1000AmountAndPlus2000Population() {
        DonorUser donorUser = DonorUserBuilder.aDonorUser().build();
        assertEquals(donorUser.getPoints(), 0);

        int donationAmount = 2000;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        donorUser.donate(donationAmount, comment, project);

        assertEquals(donorUser.getPoints(), donationAmount);
    }

    @Test
    public void testDonorUserPointsWithPlus1000AmountAndLess2000Population() {
        DonorUser donorUser = DonorUserBuilder.aDonorUser().build();
        assertEquals(donorUser.getPoints(), 0);

        int donationAmount = 2000;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(1700);
        donorUser.donate(donationAmount, comment, project);

        assertEquals(donorUser.getPoints(), donationAmount * 2);
    }

    @Test
    public void testDonorUserPointsWithLastDonationOnSameMonth() {
        DonorUser donorUser = DonorUserBuilder.aDonorUser().build();
        assertEquals(donorUser.getPoints(), 0);

        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3300);

        donorUser.donate(500, "First donation", project);
        assertEquals(donorUser.getPoints(), 0);
        assertEquals(donorUser.getDonations().size(), 1);

        int donationAmount = 2500;
        String comment = "This is my donation";
        donorUser.donate(donationAmount, comment, project);
        assertEquals(donorUser.getPoints(), donationAmount + 500);
    }

}