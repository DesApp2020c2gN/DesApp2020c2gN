package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DonationTest {

    @Test
    public void testDonationDonorUser() {
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withNickname("Juan2001").build();
        Donation donation = DonationBuilder.aDonation().withDonorUser(donorUser).build();

        assertEquals(donorUser, donation.getDonorUser());
    }

    @Test
    public void testDonationProject() {
        Project project = ProjectBuilder.aProject().withName("Conectando San Cristobal").build();
        Donation donation = DonationBuilder.aDonation().withProject(project).build();

        assertEquals(project, donation.getProject());
    }

    @Test
    public void testDonationAmount() {
        BigDecimal amount = new BigDecimal(2530);
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

    @Test
    public void testDonationWithNoPoints() {
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.getLastDonation()).thenReturn(Optional.empty());
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        BigDecimal amount = new BigDecimal(500);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals(0, points);
    }

    @Test
    public void testDonationWithAmountPoints() {
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.getLastDonation()).thenReturn(Optional.empty());
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        BigDecimal amount = new BigDecimal(1500);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals(amount.intValue(), points);
    }

    @Test
    public void testDonationWithPopulationPoints() {
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.getLastDonation()).thenReturn(Optional.empty());
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(1200);
        BigDecimal amount = new BigDecimal(500);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals(amount.multiply(new BigDecimal(2)).intValue(), points);
    }

    @Test
    public void testDonationWithLastDonationPoints() {
        Donation lastDonation = mock(Donation.class);
        when(lastDonation.getDate()).thenReturn(LocalDate.now());
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.getLastDonation()).thenReturn(Optional.of(lastDonation));
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        BigDecimal amount = new BigDecimal(500);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals(donation.pointsFromLastDonationOnSameMonth(), points);
    }

    @Test
    public void testDonationWithAmountAndLastDonationPoints() {
        Donation lastDonation = mock(Donation.class);
        when(lastDonation.getDate()).thenReturn(LocalDate.now());
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.getLastDonation()).thenReturn(Optional.of(lastDonation));
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        BigDecimal amount = new BigDecimal(4000);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals(amount.add(new BigDecimal(donation.pointsFromLastDonationOnSameMonth())).intValue(), points);
    }

    @Test
    public void testDonationWithPopulationAndLastDonationPoints() {
        Donation lastDonation = mock(Donation.class);
        when(lastDonation.getDate()).thenReturn(LocalDate.now());
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.getLastDonation()).thenReturn(Optional.of(lastDonation));
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(900);
        BigDecimal amount = new BigDecimal(700);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals((amount.multiply(new BigDecimal(2))).add(new BigDecimal(donation.pointsFromLastDonationOnSameMonth())).intValue(), points);
    }

    @Test
    public void testDonationWithAmountAndPopulationAndLastDonationPoints() {
        Donation lastDonation = mock(Donation.class);
        when(lastDonation.getDate()).thenReturn(LocalDate.now());
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.getLastDonation()).thenReturn(Optional.of(lastDonation));
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(900);
        BigDecimal amount = new BigDecimal(5000);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals((amount.multiply(new BigDecimal(2))).add(new BigDecimal(donation.pointsFromLastDonationOnSameMonth())).intValue(), points);
    }

}