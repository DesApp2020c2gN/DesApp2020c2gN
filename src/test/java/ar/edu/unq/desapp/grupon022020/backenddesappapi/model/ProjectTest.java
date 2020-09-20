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

public class ProjectTest {

    @Test
    public void testProjectName() {
        String name = "Conectar San Juan";
        Project project = ProjectBuilder.aProject().withName(name).build();

        assertEquals(name, project.getName());
    }

    @Test
    public void testProjectFactor() {
        int factor = 1200;
        Project project = ProjectBuilder.aProject().withFactor(factor).build();

        assertEquals(factor, project.getFactor());
    }

    @Test
    public void testProjectClosurePercentage() {
        int closurePercentage = 75;
        Project project = ProjectBuilder.aProject().withClosurePercentage(closurePercentage).build();

        assertEquals(closurePercentage, project.getClosurePercentage());
    }

    @Test
    public void testProjectStartDate() {
        LocalDate startDate = LocalDate.parse("2020-09-15");
        Project project = ProjectBuilder.aProject().withStartDate(startDate).build();

        assertEquals(startDate, project.getStartDate());
    }

    @Test
    public void testProjectFinishDate() {
        LocalDate finishDate = LocalDate.parse("2020-12-01");
        Project project = ProjectBuilder.aProject().withFinishDate(finishDate).build();

        assertEquals(finishDate, project.getFinishDate());
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
    public void testProjectLocation() {
        Location location = mock(Location.class);
        Project project = ProjectBuilder.aProject().withLocation(location).build();

        assertEquals(location, project.getLocation());
    }

    @Test
    public void testProjectTotalDonations() {
        int amount_1 = 1200;
        int amount_2 = 1750;
        int amount_3 = 700;
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

        assertEquals(amount_1 + amount_2 + amount_3, project.totalAmountDonations());
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
                withFinishDate(LocalDate.now().plusDays(1)).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                build();
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(9999999).build();

        donorUser.donate(2000000, "First donation", project);
        donorUser.donate(2000000, "Second donation", project);

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
                withFinishDate(LocalDate.now().plusDays(1)).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                build();
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(9999999).build();

        donorUser.donate(1000000, "First donation", project);
        donorUser.donate(3000000, "Second donation", project);

        assertFalse(project.hasReachedGoal());
    }
}
