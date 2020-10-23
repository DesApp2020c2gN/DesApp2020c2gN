package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DonorTest {

    @Test
    public void testDonorUserName() {
        String name = "Marcelo";
        Donor donor = DonorBuilder.aDonorUser().withName(name).build();
        assertEquals(name, donor.getName());
    }

    @Test
    public void testDonorUserNameSetter() {
        String name = "Marcelo";
        Donor donor = DonorBuilder.aDonorUser().build();
        donor.setName(name);
        assertEquals(name, donor.getName());
    }

    @Test
    public void testDonorUserNickname() {
        String nickname = "Marce98";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).build();
        assertEquals(nickname, donor.getNickname());
    }

    @Test
    public void testDonorUserNicknameSetter() {
        String nickname = "Marce98";
        Donor donor = DonorBuilder.aDonorUser().build();
        donor.setNickname(nickname);
        assertEquals(nickname, donor.getNickname());
    }

    @Test
    public void testDonorUserMail() {
        String mail = "marce98@gmail.com";
        Donor donor = DonorBuilder.aDonorUser().withMail(mail).build();
        assertEquals(mail, donor.getMail());
    }

    @Test
    public void testDonorUserMailSetter() {
        String mail = "marce98@gmail.com";
        Donor donor = DonorBuilder.aDonorUser().build();
        donor.setMail(mail);
        assertEquals(mail, donor.getMail());
    }

    @Test
    public void testDonorUserPassword() {
        String password = "marce_98_1001";
        Donor donor = DonorBuilder.aDonorUser().withPassword(password).build();
        assertEquals(password, donor.getPassword());
    }

    @Test
    public void testDonorUserPasswordSetter() {
        String password = "marce_98_1001";
        Donor donor = DonorBuilder.aDonorUser().build();
        donor.setPassword(password);
        assertEquals(password, donor.getPassword());
    }

    @Test
    public void testDonorUserDonationList() {
        List<Donation> donations = new ArrayList<>();
        Donation donation_1 = mock(Donation.class);
        Donation donation_2 = mock(Donation.class);
        donations.add(donation_1);
        donations.add(donation_2);
        Donor donor = DonorBuilder.aDonorUser().withDonations(donations).build();
        assertEquals(donations, donor.getDonations());
    }

    @Test
    public void testDonorUserDonationListSetter() {
        List<Donation> donations = new ArrayList<>();
        Donation donation_1 = mock(Donation.class);
        Donation donation_2 = mock(Donation.class);
        donations.add(donation_1);
        donations.add(donation_2);
        Donor donor = DonorBuilder.aDonorUser().build();
        donor.setDonations(donations);
        assertEquals(donations, donor.getDonations());
    }

    @Test
    public void testDonorUserPoints() {
        int points = 71;
        Donor donor = DonorBuilder.aDonorUser().withPoints(points).build();
        assertEquals(points, donor.getPoints());
    }

    @Test
    public void testDonorUserPointsSetter() {
        int points = 71;
        Donor donor = DonorBuilder.aDonorUser().build();
        donor.setPoints(points);
        assertEquals(points, donor.getPoints());
    }

    @Test
    public void testDonorUserMoney() {
        BigDecimal money = new BigDecimal(2300);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        assertEquals(money, donor.getMoney());
    }

    @Test
    public void testDonorUserMoneySetter() {
        BigDecimal money = new BigDecimal(2300);
        Donor donor = DonorBuilder.aDonorUser().build();
        donor.setMoney(money);
        assertEquals(money, donor.getMoney());
    }

    @Test
    public void testDonorUserDonation() throws InvalidDonationException {
        BigDecimal money = new BigDecimal(9999);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        assertTrue(donor.getDonations().isEmpty());

        BigDecimal amount = new BigDecimal(3570);
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donor.donate(amount, comment, project);
        assertFalse(donor.getDonations().isEmpty());

        Donation donation = donor.getDonations().get(0);
        assertEquals(amount, donation.getAmount());
        assertEquals(comment, donation.getComment());
        assertEquals(money.subtract(amount), donor.getMoney());
    }

    @Test
    public void testDonorUserPointsWithLess1000AmountAndPlus2000Population() throws InvalidDonationException {
        BigDecimal money = new BigDecimal(9999);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        assertEquals(0, donor.getPoints());

        BigDecimal donationAmount = new BigDecimal(500);
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(3000);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donor.donate(donationAmount, comment, project);

        assertEquals(0, donor.getPoints());
    }

    @Test
    public void testDonorUserPointsWithPlus1000AmountAndPlus2000Population() throws InvalidDonationException {
        BigDecimal money = new BigDecimal(9999);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        assertEquals(0, donor.getPoints());

        BigDecimal donationAmount = new BigDecimal(2000);
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(3000);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donor.donate(donationAmount, comment, project);

        assertEquals(donationAmount.intValue(), donor.getPoints());
    }

    @Test
    public void testDonorUserPointsWithPlus1000AmountAndLess2000Population() throws InvalidDonationException {
        BigDecimal money = new BigDecimal(9999);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        assertEquals(0, donor.getPoints());

        BigDecimal donationAmount = new BigDecimal(2000);
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(1700);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donor.donate(donationAmount, comment, project);

        assertEquals(donationAmount.multiply(new BigDecimal(2)).intValue(), donor.getPoints());
    }

    @Test
    public void testDonorUserPointsWithLastDonationNotOnSameMonth() throws InvalidDonationException {
        BigDecimal money = new BigDecimal(9999);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        Donation lastDonation = DonationBuilder.aDonation().withDate(LocalDate.now().minusMonths(7)).build();
        List<Donation> donations = new ArrayList<>();
        donations.add(lastDonation);
        Location location = mock(Location.class);
        when(location.getPopulation()).thenReturn(8000);
        Project project = ProjectBuilder.aProject().withLocation(location).withDonations(donations).build();
        assertEquals(0, donor.getPoints());

        BigDecimal donationAmount = new BigDecimal(2500);
        String comment = "This is my donation";
        donor.donate(donationAmount, comment, project);
        assertEquals(donationAmount.intValue(), donor.getPoints());
    }

    @Test
    public void testDonorUserPointsWithLastDonationOnSameMonth() throws InvalidDonationException {
        BigDecimal money = new BigDecimal(9999);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        assertEquals(0, donor.getPoints());

        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(3300);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        donor.donate(new BigDecimal(500), "Second donation", project);
        assertEquals(0, donor.getPoints());
        assertEquals(1, donor.getDonations().size());

        BigDecimal donationAmount = new BigDecimal(2500);
        String comment = "This is my donation";
        donor.donate(donationAmount, comment, project);
        assertEquals(donationAmount.add(new BigDecimal(500)).intValue(), donor.getPoints());
    }

    @Test
    public void testUserDonatesOnProjectNotStarted() {
        BigDecimal money = new BigDecimal(9999);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        Location location = mock(Location.class);
        when(location.getPopulation()).thenReturn(2000);
        LocalDate startDate = LocalDate.now().plusDays(5);
        Project project = ProjectBuilder.aProject().withStartDate(startDate).withLocation(location).build();

        try
        {
            donor.donate(new BigDecimal(900), "Third donation", project);
        }
        catch(InvalidDonationException e)
        {
            String message = "Project " + project.getName() + " has not started";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserDonatesOnClosedProject() {
        BigDecimal money = new BigDecimal(9999);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        Location location = mock(Location.class);
        when(location.getPopulation()).thenReturn(3300);
        Project project = ProjectBuilder.aProject().withStartDate(LocalDate.now().plusDays(-3)).withDurationInDays(1).withLocation(location).build();

        try
        {
            donor.donate(new BigDecimal(500), "Fourth donation", project);
        }
        catch(InvalidDonationException e)
        {
            String message = "Project " + project.getName() + " has finished";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserDonatesWithoutMoney() {
        BigDecimal money = new BigDecimal(1000);
        Donor donor = DonorBuilder.aDonorUser().withMoney(money).build();
        Location location = mock(Location.class);
        when(location.getPopulation()).thenReturn(1000);
        Project project = ProjectBuilder.aProject().withDurationInDays(7).withLocation(location).build();

        try
        {
            donor.donate(new BigDecimal(2000), "Fifth donation", project);
        }
        catch(InvalidDonationException e)
        {
            String message = "User " + donor.getNickname() + " does not have enough money";
            assertEquals(message, e.getMessage());
        }
    }

}