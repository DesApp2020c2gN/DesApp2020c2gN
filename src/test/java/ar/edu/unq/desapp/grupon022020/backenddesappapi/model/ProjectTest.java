package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

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

public class ProjectTest {

    @Test
    public void testProjectName() {
        String name = "Conectar San Juan";
        Project project = ProjectBuilder.aProject().withName(name).build();
        assertEquals(name, project.getName());
    }

    @Test
    public void testProjectNameSetter() {
        String name = "Conectar San Juan";
        Project project = ProjectBuilder.aProject().build();
        project.setName(name);
        assertEquals(name, project.getName());
    }

    @Test
    public void testProjectFactor() {
        int factor = 1200;
        Project project = ProjectBuilder.aProject().withFactor(factor).build();
        assertEquals(factor, project.getFactor());
    }

    @Test
    public void testProjectFactorSetter() {
        int factor = 1200;
        Project project = ProjectBuilder.aProject().build();
        project.setFactor(factor);
        assertEquals(factor, project.getFactor());
    }

    @Test
    public void testProjectClosurePercentage() {
        int closurePercentage = 75;
        Project project = ProjectBuilder.aProject().withClosurePercentage(closurePercentage).build();
        assertEquals(closurePercentage, project.getClosurePercentage());
    }

    @Test
    public void testProjectClosurePercentageSetter() {
        int closurePercentage = 75;
        Project project = ProjectBuilder.aProject().build();
        project.setClosurePercentage(closurePercentage);
        assertEquals(closurePercentage, project.getClosurePercentage());
    }

    @Test
    public void testProjectStartDate() {
        LocalDate startDate = LocalDate.parse("2020-09-15");
        Project project = ProjectBuilder.aProject().withStartDate(startDate).build();
        assertEquals(startDate, project.getStartDate());
    }

    @Test
    public void testProjectStartDateSetter() {
        LocalDate startDate = LocalDate.parse("2020-09-15");
        Project project = ProjectBuilder.aProject().build();
        project.setStartDate(startDate);
        assertEquals(startDate, project.getStartDate());
    }

    @Test
    public void testProjectFinishDate() {
        int durationInDays = 120;
        LocalDate finishDate = LocalDate.now().plusDays(durationInDays);
        Project project = ProjectBuilder.aProject().withDurationInDays(durationInDays).build();
        assertEquals(finishDate, project.getFinishDate());
    }

    @Test
    public void testProjectFinishDateSetter() {
        int durationInDays = 120;
        Project project = ProjectBuilder.aProject().withDurationInDays(durationInDays).build();
        LocalDate newFinishDate = LocalDate.parse("2021-11-21");
        project.setFinishDate(newFinishDate);
        assertEquals(newFinishDate, project.getFinishDate());
    }

    @Test
    public void testProjectStatus() {
        String status = ProjectStatus.ACTIVE.name();
        Project project = ProjectBuilder.aProject().withStatus(status).build();
        assertEquals(status, project.getStatus());
    }

    @Test
    public void testProjectStatusSetter() {
        String status = ProjectStatus.ACTIVE.name();
        Project project = ProjectBuilder.aProject().withStatus(status).build();
        String newStatus = ProjectStatus.COMPLETE.name();
        project.setStatus(newStatus);
        assertEquals(newStatus, project.getStatus());
    }

    @Test
    public void testProjectDonationList() {
        List<Donation> donations = new ArrayList<>();
        Donation donation_1 = mock(Donation.class);
        Donation donation_2 = mock(Donation.class);
        donations.add(donation_1);
        donations.add(donation_2);
        Project project = ProjectBuilder.aProject().withDonations(donations).build();
        assertEquals(donations, project.getDonations());
    }

    @Test
    public void testProjectDonationListSetter() {
        List<Donation> donations = new ArrayList<>();
        Donation donation_1 = mock(Donation.class);
        Donation donation_2 = mock(Donation.class);
        donations.add(donation_1);
        donations.add(donation_2);
        Project project = ProjectBuilder.aProject().build();
        project.setDonations(donations);
        assertEquals(donations, project.getDonations());
    }

    @Test
    public void testProjectLocation() {
        Location location = mock(Location.class);
        Project project = ProjectBuilder.aProject().withLocation(location).build();
        assertEquals(location, project.getLocation());
    }

    @Test
    public void testProjectLocationSetter() {
        Location location = mock(Location.class);
        Project project = ProjectBuilder.aProject().build();
        project.setLocation(location);
        assertEquals(location, project.getLocation());
    }

    @Test
    public void testProjectTotalDonations() {
        BigDecimal amount_1 = new BigDecimal(1200);
        BigDecimal amount_2 = new BigDecimal(1750);
        BigDecimal amount_3 = new BigDecimal(700);
        Donation donation_1 = mock(Donation.class);
        when(donation_1.getAmount()).thenReturn(amount_1);
        Donation donation_2 = mock(Donation.class);
        when(donation_2.getAmount()).thenReturn(amount_2);
        Donation donation_3 = mock(Donation.class);
        when(donation_3.getAmount()).thenReturn(amount_3);
        List<Donation> donations = new ArrayList<>();
        donations.add(donation_1);
        donations.add(donation_2);
        donations.add(donation_3);
        Project project = ProjectBuilder.aProject().withDonations(donations).build();

        assertEquals(amount_1.add(amount_2).add(amount_3) , project.totalAmountDonations());
    }

    @Test
    public void testProjectPercentageAchieved() {
        BigDecimal amount_1 = new BigDecimal(200);
        BigDecimal amount_2 = new BigDecimal(170);
        BigDecimal amount_3 = new BigDecimal(300);
        Donation donation_1 = mock(Donation.class);
        when(donation_1.getAmount()).thenReturn(amount_1);
        Donation donation_2 = mock(Donation.class);
        when(donation_2.getAmount()).thenReturn(amount_2);
        Donation donation_3 = mock(Donation.class);
        when(donation_3.getAmount()).thenReturn(amount_3);
        List<Donation> donations = new ArrayList<>();
        donations.add(donation_1);
        donations.add(donation_2);
        donations.add(donation_3);
        int factor = 3400;
        Project project = ProjectBuilder.aProject().withFactor(factor).withDonations(donations).build();

        float expectedPercentageAchieved = ((float) (amount_1.intValue() + amount_2.intValue() + amount_3.intValue()) / project.moneyRequired()) * 100;
        assertEquals(expectedPercentageAchieved, project.percentageAchieved());
    }

    @Test
    public void testProjectWithAchievedGoal() throws InvalidDonationException {
        Location location = mock(Location.class);
        int population = 1000;
        when(location.getPopulation()).thenReturn(population);
        int factor = 5000;
        int closurePercentage = 50;
        Project project = ProjectBuilder.aProject().
                withLocation(location).
                withStartDate(LocalDate.now().plusDays(-1)).
                withDurationInDays(1).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                build();
        Donor donor = DonorBuilder.aDonorUser().withMoney(new BigDecimal(9999999)).build();

        donor.donate(new BigDecimal(2000000), "First donation", project);
        donor.donate(new BigDecimal(2000000), "Second donation", project);

        assertTrue(project.hasReachedGoal());
    }

    @Test
    public void testProjectThatDoesNotReachGoal() throws InvalidDonationException {
        Location location = mock(Location.class);
        int population = 1000;
        when(location.getPopulation()).thenReturn(population);
        int factor = 5000;
        int closurePercentage = 90;
        Project project = ProjectBuilder.aProject().
                withLocation(location).
                withStartDate(LocalDate.now().plusDays(-1)).
                withDurationInDays(1).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                build();
        Donor donor = DonorBuilder.aDonorUser().withMoney(new BigDecimal(9999999)).build();

        donor.donate(new BigDecimal(1000000), "First donation", project);
        donor.donate(new BigDecimal(3000000), "Second donation", project);

        assertFalse(project.hasReachedGoal());
    }

    @Test
    public void testProjectNumberOfDonors() {
        String donorNickname_1 = "juan123";
        String donorNickname_2 = "maria321";
        String donorNickname_3 = "ariel999";
        Donation donation_1 = mock(Donation.class);
        when(donation_1.getDonorNickname()).thenReturn(donorNickname_1);
        Donation donation_2 = mock(Donation.class);
        when(donation_2.getDonorNickname()).thenReturn(donorNickname_2);
        Donation donation_3 = mock(Donation.class);
        when(donation_3.getDonorNickname()).thenReturn(donorNickname_3);
        Donation donation_4 = mock(Donation.class);
        when(donation_4.getDonorNickname()).thenReturn(donorNickname_2);
        List<Donation> donations = new ArrayList<>();
        donations.add(donation_1);
        donations.add(donation_2);
        donations.add(donation_3);
        donations.add(donation_4);
        Project project = ProjectBuilder.aProject().withDonations(donations).build();

        assertEquals(3, project.numberOfDonors());
    }
}
