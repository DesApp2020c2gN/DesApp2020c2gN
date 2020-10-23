package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DonationTest {

    @Test
    public void testDonationDonorNickname() {
        String donorNickname = "Juan2001";
        Donation donation = DonationBuilder.aDonation().withDonorNickname(donorNickname).build();
        assertEquals(donorNickname, donation.getDonorNickname());
    }

    @Test
    public void testDonationDonorNicknameSetter() {
        String donorNickname = "Juan2001";
        Donation donation = DonationBuilder.aDonation().build();
        donation.setDonorNickname(donorNickname);
        assertEquals(donorNickname, donation.getDonorNickname());
    }

    @Test
    public void testDonationProjectName() {
        String projectName = "Conectando San Cristobal";
        Donation donation = DonationBuilder.aDonation().withProjectName(projectName).build();
        assertEquals(projectName, donation.getProjectName());
    }

    @Test
    public void testDonationProjectNameSetter() {
        String projectName = "Conectando San Cristobal";
        Donation donation = DonationBuilder.aDonation().build();
        donation.setProjectName(projectName);
        assertEquals(projectName, donation.getProjectName());
    }

    @Test
    public void testDonationAmount() {
        BigDecimal amount = new BigDecimal(2530);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();
        assertEquals(amount, donation.getAmount());
    }

    @Test
    public void testDonationAmountSetter() {
        BigDecimal amount = new BigDecimal(2530);
        Donation donation = DonationBuilder.aDonation().build();
        donation.setAmount(amount);
        assertEquals(amount, donation.getAmount());
    }

    @Test
    public void testDonationComment() {
        String comment = "This is a donation";
        Donation donation = DonationBuilder.aDonation().withComment(comment).build();
        assertEquals(comment, donation.getComment());
    }

    @Test
    public void testDonationCommentSetter() {
        String comment = "This is a donation";
        Donation donation = DonationBuilder.aDonation().build();
        donation.setComment(comment);
        assertEquals(comment, donation.getComment());
    }

    @Test
    public void testDonationDate() {
        LocalDate date = LocalDate.now();
        Donation donation = DonationBuilder.aDonation().withDate(date).build();
        assertEquals(date, donation.getDate());
    }

    @Test
    public void testDonationDateSetter() {
        LocalDate date = LocalDate.now();
        Donation donation = DonationBuilder.aDonation().build();
        donation.setDate(date);
        assertEquals(date, donation.getDate());
    }

    @Test
    public void testDonationWithNoPoints() {
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.lastDonation()).thenReturn(Optional.empty());
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(3000);
        BigDecimal amount = new BigDecimal(500);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals(0, points);
    }

    @Test
    public void testDonationWithAmountPoints() {
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.lastDonation()).thenReturn(Optional.empty());
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(3000);
        BigDecimal amount = new BigDecimal(1500);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals(amount.intValue(), points);
    }

    @Test
    public void testDonationWithPopulationPoints() {
        DonorUser donorUser = mock(DonorUser.class);
        when(donorUser.lastDonation()).thenReturn(Optional.empty());
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(1200);
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
        when(donorUser.lastDonation()).thenReturn(Optional.of(lastDonation));
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(3000);
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
        when(donorUser.lastDonation()).thenReturn(Optional.of(lastDonation));
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(3000);
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
        when(donorUser.lastDonation()).thenReturn(Optional.of(lastDonation));
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(900);
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
        when(donorUser.lastDonation()).thenReturn(Optional.of(lastDonation));
        Project project = mock(Project.class);
        when(project.locationPopulation()).thenReturn(900);
        BigDecimal amount = new BigDecimal(5000);
        Donation donation = DonationBuilder.aDonation().withAmount(amount).build();

        int points = donation.calculatePoints(donorUser, project);
        assertEquals((amount.multiply(new BigDecimal(2))).add(new BigDecimal(donation.pointsFromLastDonationOnSameMonth())).intValue(), points);
    }

}