package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.SystemBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SystemTest {

    @Test
    public void testSystemOpenProjects() {
        Project project_1 = ProjectBuilder.aProject().build();
        Project project_2 = ProjectBuilder.aProject().build();
        List<Project> openProjects = new ArrayList<>();
        openProjects.add(project_1);
        openProjects.add(project_2);
        System system = SystemBuilder.aSystem().withOpenProjects(openProjects).build();

        assertTrue(system.getOpenProjects().size() == 2);
        assertTrue(system.getOpenProjects().contains(project_1));
        assertTrue(system.getOpenProjects().contains(project_2));
    }

    @Test
    public void testSystemClosedProjects() {
        Project project_1 = ProjectBuilder.aProject().build();
        Project project_2 = ProjectBuilder.aProject().build();
        List<Project> closedProjects = new ArrayList<>();
        closedProjects.add(project_1);
        closedProjects.add(project_2);
        System system = SystemBuilder.aSystem().withClosedProjects(closedProjects).build();

        assertTrue(system.getClosedProjects().size() == 2);
        assertTrue(system.getClosedProjects().contains(project_1));
        assertTrue(system.getClosedProjects().contains(project_2));
    }

    @Test
    public void testSystemUsers() {
        DonorUser donorUser_1 = DonorUserBuilder.aDonorUser().build();
        DonorUser donorUser_2 = DonorUserBuilder.aDonorUser().build();
        List<DonorUser> donorUsers = new ArrayList<>();
        donorUsers.add(donorUser_1);
        donorUsers.add(donorUser_2);
        System system = SystemBuilder.aSystem().withUsers(donorUsers).build();

        assertTrue(system.getUsers().size() == 2);
        assertTrue(system.getUsers().contains(donorUser_1));
        assertTrue(system.getUsers().contains(donorUser_2));
    }

    @Test
    public void testSystemLocations() {
        Location location_1 = LocationBuilder.aLocation().build();
        Location location_2 = LocationBuilder.aLocation().build();
        List<Location> locations = new ArrayList<>();
        locations.add(location_1);
        locations.add(location_2);
        System system = SystemBuilder.aSystem().withLocations(locations).build();

        assertTrue(system.getLocations().size() == 2);
        assertTrue(system.getLocations().contains(location_1));
        assertTrue(system.getLocations().contains(location_2));
    }

    @Test
    public void testSystemCloseFinishedProjects() {
        Project project_1 = ProjectBuilder.aProject().withFinishDate(LocalDate.now()).build();
        Project project_2 = ProjectBuilder.aProject().withFinishDate(LocalDate.now()).build();
        Project project_3 = ProjectBuilder.aProject().withFinishDate(LocalDate.now().plusDays(5)).build();
        List<Project> projects = new ArrayList<>();
        projects.add(project_1);
        projects.add(project_2);
        projects.add(project_3);
        System system = SystemBuilder.aSystem().withOpenProjects(projects).build();

        assertEquals(3, system.getOpenProjects().size());
        assertEquals(0, system.getClosedProjects().size());
        system.closeFinishedProjects();
        assertEquals(1, system.getOpenProjects().size());
        assertEquals(2, system.getClosedProjects().size());
    }

    @Test
    public void testSystemTopTenDonations() {
        DonorUser donorUser_1 = mock(DonorUser.class);
        Donation donation_1_1 = DonationBuilder.aDonation().withAmount(1200).build();
        Donation donation_1_2 = DonationBuilder.aDonation().withAmount(700).build();
        Donation donation_1_3 = DonationBuilder.aDonation().withAmount(870).build();
        Donation donation_1_4 = DonationBuilder.aDonation().withAmount(3000).build();
        List<Donation> donations_1 = new ArrayList<>();
        donations_1.add(donation_1_1);
        donations_1.add(donation_1_2);
        donations_1.add(donation_1_3);
        donations_1.add(donation_1_4);
        when(donorUser_1.getDonations()).thenReturn(donations_1);

        DonorUser donorUser_2 = mock(DonorUser.class);
        Donation donation_2_1 = DonationBuilder.aDonation().withAmount(450).build();
        Donation donation_2_2 = DonationBuilder.aDonation().withAmount(2100).build();
        Donation donation_2_3 = DonationBuilder.aDonation().withAmount(4200).build();
        Donation donation_2_4 = DonationBuilder.aDonation().withAmount(120).build();
        List<Donation> donations_2 = new ArrayList<>();
        donations_2.add(donation_2_1);
        donations_2.add(donation_2_2);
        donations_2.add(donation_2_3);
        donations_2.add(donation_2_4);
        when(donorUser_2.getDonations()).thenReturn(donations_2);

        DonorUser donorUser_3 = mock(DonorUser.class);
        Donation donation_3_1 = DonationBuilder.aDonation().withAmount(1750).build();
        Donation donation_3_2 = DonationBuilder.aDonation().withAmount(1000).build();
        Donation donation_3_3 = DonationBuilder.aDonation().withAmount(200).build();
        Donation donation_3_4 = DonationBuilder.aDonation().withAmount(3290).build();
        List<Donation> donations_3 = new ArrayList<>();
        donations_3.add(donation_3_1);
        donations_3.add(donation_3_2);
        donations_3.add(donation_3_3);
        donations_3.add(donation_3_4);
        when(donorUser_3.getDonations()).thenReturn(donations_3);

        List<DonorUser> donorUsers = new ArrayList<>();
        donorUsers.add(donorUser_1);
        donorUsers.add(donorUser_2);
        donorUsers.add(donorUser_3);
        System system = SystemBuilder.aSystem().withUsers(donorUsers).build();

        List<Donation> topTenDonations = system.getTopTenBiggestDonations();
        assertTrue(topTenDonations.size() == 10);
    }

}