package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DonorUserTest {

    @Test
    public void testDonorUserName() {
        String name = "Marcelo";
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withName(name).build();

        assertEquals(name, donorUser.getName());
    }

    @Test
    public void testDonorUserNickname() {
        String nickname = "Marce98";
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withNickname(nickname).build();

        assertEquals(nickname, donorUser.getNickname());
    }

    @Test
    public void testDonorUserMail() {
        String mail = "marce98@gmail.com";
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMail(mail).build();

        assertEquals(mail, donorUser.getMail());
    }

    @Test
    public void testDonorUserPassword() {
        String password = "marce_98_1001";
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withPassword(password).build();

        assertEquals(password, donorUser.getPassword());
    }

    @Test
    public void testDonorUserDonationList() {
        List<Donation> donations = new ArrayList<>();
        Donation donation_1 = mock(Donation.class);
        Donation donation_2 = mock(Donation.class);
        donations.add(donation_1);
        donations.add(donation_2);

        DonorUser donorUser = DonorUserBuilder.aDonorUser().withDonations(donations).build();

        assertEquals(donations, donorUser.getDonations());
    }

    @Test
    public void testDonorUserPoints() {
        int points = 71;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withPoints(points).build();

        assertEquals(points, donorUser.getPoints());
    }

    @Test
    public void testDonorUserMoney() {
        int money = 2300;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(money).build();

        assertEquals(money, donorUser.getMoney());
    }

    @Test
    public void testDonorUserDonation() throws InvalidDonationException {
        int money = 9999;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(money).build();
        assertTrue(donorUser.getDonations().isEmpty());

        int amount = 3570;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donorUser.donate(amount, comment, project);
        assertFalse(donorUser.getDonations().isEmpty());

        Donation donation = donorUser.getDonations().get(0);
        assertEquals(amount, donation.getAmount());
        assertEquals(comment, donation.getComment());
        assertEquals(money - amount, donorUser.getMoney());
    }

    @Test
    public void testDonorUserPointsWithLess1000AmountAndPlus2000Population() throws InvalidDonationException {
        int money = 9999;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(money).build();
        assertEquals(0, donorUser.getPoints());

        int donationAmount = 500;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donorUser.donate(donationAmount, comment, project);

        assertEquals(0, donorUser.getPoints());
    }

    @Test
    public void testDonorUserPointsWithPlus1000AmountAndPlus2000Population() throws InvalidDonationException {
        int money = 9999;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(money).build();
        assertEquals(0, donorUser.getPoints());

        int donationAmount = 2000;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donorUser.donate(donationAmount, comment, project);

        assertEquals(donationAmount, donorUser.getPoints());
    }

    @Test
    public void testDonorUserPointsWithPlus1000AmountAndLess2000Population() throws InvalidDonationException {
        int money = 9999;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(money).build();
        assertEquals(0, donorUser.getPoints());

        int donationAmount = 2000;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(1700);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donorUser.donate(donationAmount, comment, project);

        assertEquals(donationAmount * 2, donorUser.getPoints());
    }

    @Test
    public void testDonorUserPointsWithLastDonationOnSameMonth() throws InvalidDonationException {
        int money = 9999;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(money).build();
        assertEquals(0, donorUser.getPoints());

        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3300);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donorUser.donate(500, "First donation", project);
        assertEquals(0, donorUser.getPoints());
        assertEquals(1, donorUser.getDonations().size());

        int donationAmount = 2500;
        String comment = "This is my donation";
        donorUser.donate(donationAmount, comment, project);
        assertEquals(donationAmount + 500, donorUser.getPoints());
    }

    @Test
    public void testUserDonatesOnProjectNotStarted() {
        int money = 9999;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(money).build();
        Location location = mock(Location.class);
        when(location.getPopulation()).thenReturn(2000);
        LocalDate startDate = LocalDate.now().plusDays(5);
        Project project = ProjectBuilder.aProject().withStartDate(startDate).withLocation(location).build();

        try
        {
            donorUser.donate(900, "First donation", project);
        }
        catch(InvalidDonationException e)
        {
            String message = "Project " + project.getName() + " has not started";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserDonatesOnClosedProject() {
        int money = 9999;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(money).build();
        Location location = mock(Location.class);
        when(location.getPopulation()).thenReturn(3300);
        LocalDate finishDate = LocalDate.now().plusDays(-1);
        Project project = ProjectBuilder.aProject().withFinishDate(finishDate).withLocation(location).build();

        try
        {
            donorUser.donate(500, "Second donation", project);
        }
        catch(InvalidDonationException e)
        {
            String message = "Project " + project.getName() + " has finished";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserDonatesWithoutMoney() {
        int money = 1000;
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(money).build();
        Location location = mock(Location.class);
        when(location.getPopulation()).thenReturn(1000);
        LocalDate finishDate = LocalDate.now().plusDays(7);
        Project project = ProjectBuilder.aProject().withFinishDate(finishDate).withLocation(location).build();

        try
        {
            donorUser.donate(2000, "Third donation", project);
        }
        catch(InvalidDonationException e)
        {
            String message = "User " + donorUser.getName() + " does not have enough money";
            assertEquals(message, e.getMessage());
        }
    }

}