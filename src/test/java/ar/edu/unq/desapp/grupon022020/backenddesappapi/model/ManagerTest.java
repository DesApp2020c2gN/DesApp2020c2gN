package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.*;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

class ManagerTest {

    @Test
    public void testManagerOpenProjects() {
        Project project_1 = ProjectBuilder.aProject().build();
        Project project_2 = ProjectBuilder.aProject().build();
        List<Project> openProjects = new ArrayList<>();
        openProjects.add(project_1);
        openProjects.add(project_2);
        Manager manager = ManagerBuilder.aManager().withOpenProjects(openProjects).build();

        assertEquals(2, manager.getOpenProjects().size());
        assertTrue(manager.getOpenProjects().contains(project_1));
        assertTrue(manager.getOpenProjects().contains(project_2));
    }

    @Test
    public void testManagerOpenProjectsEndingThisMonth() {
        Project project_1 = ProjectBuilder.aProject().withDurationInDays(0).build();
        Project project_2 = ProjectBuilder.aProject().withDurationInDays(-90).build();
        Project project_3 = ProjectBuilder.aProject().withDurationInDays(-150).build();
        List<Project> openProjects = new ArrayList<>();
        openProjects.add(project_1);
        openProjects.add(project_2);
        openProjects.add(project_3);
        Manager manager = ManagerBuilder.aManager().withOpenProjects(openProjects).build();

        assertEquals(1, manager.getOpenProjectsEndingThisMonth().size());
        assertTrue(manager.getOpenProjectsEndingThisMonth().contains(project_1));
    }

    @Test
    public void testManagerClosedProjects() {
        Project project_1 = ProjectBuilder.aProject().build();
        Project project_2 = ProjectBuilder.aProject().build();
        List<Project> closedProjects = new ArrayList<>();
        closedProjects.add(project_1);
        closedProjects.add(project_2);
        Manager manager = ManagerBuilder.aManager().withClosedProjects(closedProjects).build();

        assertEquals(2, manager.getClosedProjects().size());
        assertTrue(manager.getClosedProjects().contains(project_1));
        assertTrue(manager.getClosedProjects().contains(project_2));
    }

    @Test
    public void testManagerUsers() {
        DonorUser donorUser_1 = DonorUserBuilder.aDonorUser().build();
        DonorUser donorUser_2 = DonorUserBuilder.aDonorUser().build();
        List<DonorUser> donorUsers = new ArrayList<>();
        donorUsers.add(donorUser_1);
        donorUsers.add(donorUser_2);
        Manager manager = ManagerBuilder.aManager().withUsers(donorUsers).build();

        assertEquals(2, manager.getUsers().size());
        assertTrue(manager.getUsers().contains(donorUser_1));
        assertTrue(manager.getUsers().contains(donorUser_2));
    }

    @Test
    public void testManagerAddNewUser() {
        DonorUser donorUser_1 = DonorUserBuilder.aDonorUser().build();
        DonorUser donorUser_2 = DonorUserBuilder.aDonorUser().build();
        Manager manager = ManagerBuilder.aManager().build();
        manager.addNewDonorUser(donorUser_1);
        manager.addNewDonorUser(donorUser_2);
        assertEquals(2, manager.getUsers().size());
        assertTrue(manager.getUsers().contains(donorUser_1));
        assertTrue(manager.getUsers().contains(donorUser_2));
    }

    @Test
    public void testManagerLocations() {
        Location location_1 = LocationBuilder.aLocation().build();
        Location location_2 = LocationBuilder.aLocation().build();
        List<Location> locations = new ArrayList<>();
        locations.add(location_1);
        locations.add(location_2);
        Manager manager = ManagerBuilder.aManager().withLocations(locations).build();

        assertEquals(2, manager.getLocations().size());
        assertTrue(manager.getLocations().contains(location_1));
        assertTrue(manager.getLocations().contains(location_2));
    }

    @Test
    public void testManagerCloseFinishedProjects() {
        Project project_1 = ProjectBuilder.aProject().withDurationInDays(0).build();
        Project project_2 = ProjectBuilder.aProject().withDurationInDays(0).build();
        Project project_3 = ProjectBuilder.aProject().withDurationInDays(5).build();
        List<Project> projects = new ArrayList<>();
        projects.add(project_1);
        projects.add(project_2);
        projects.add(project_3);
        Manager manager = ManagerBuilder.aManager().withOpenProjects(projects).build();

        assertEquals(3, manager.getOpenProjects().size());
        assertEquals(0, manager.getClosedProjects().size());
        manager.closeFinishedProjects();
        assertEquals(1, manager.getOpenProjects().size());
        assertEquals(2, manager.getClosedProjects().size());
    }

    @Test
    public void testManagerFinishedButNotCompletedProjects() throws InvalidProjectOperation {
        LocalDate startDate = LocalDate.now().minusDays(1);
        Location location_1 = LocationBuilder.aLocation().withName("Mercedes").build();
        Location location_2 = LocationBuilder.aLocation().withName("Tandil").build();
        Project project_1 = ProjectBuilder.aProject().withStartDate(startDate).withLocation(location_1).build();
        Project project_2 = ProjectBuilder.aProject().withStartDate(startDate).withLocation(location_2).build();
        Manager manager = ManagerBuilder.aManager().build();

        manager.addNewProject(project_1);
        manager.closeFinishedProjects();
        manager.addNewProject(project_2);
        assertTrue(manager.getClosedProjects().contains(project_1));
        assertTrue(manager.getOpenProjects().contains(project_2));
    }

    @Test
    public void testManagerAlreadyCompletedProjects() throws InvalidDonationException, InvalidProjectOperation {
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withMoney(new BigDecimal(9000)).build();
        Manager manager = ManagerBuilder.aManager().build();
        manager.addNewDonorUser(donorUser);
        int factor = 100;
        int closurePercentage = 85;
        Location location = LocationBuilder.aLocation().build();
        LocalDate startDate = LocalDate.now().minusDays(1);
        Project project_1 = ProjectBuilder.aProject().withFactor(factor).withClosurePercentage(closurePercentage).withStartDate(startDate).withLocation(location).build();
        manager.addNewProject(project_1);
        donorUser.donate(new BigDecimal(700), "Donation", project_1);
        manager.closeFinishedProjects();
        Project project_2 = ProjectBuilder.aProject().withFactor(factor).withClosurePercentage(closurePercentage).withLocation(location).build();

        try {
            manager.addNewProject(project_2);
        } catch (InvalidProjectOperation e) {
            String message = "A project for location " + location.getName() + " is already completed";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testManagerReturnsDonationsForFinishedIncompleteProjects() throws InvalidDonationException {
        DonorUser donorUser_1 = DonorUserBuilder.aDonorUser().withNickname("Ana1970").withMoney(new BigDecimal(1500)).build();
        DonorUser donorUser_2 = DonorUserBuilder.aDonorUser().withNickname("Juan2001").withMoney(new BigDecimal(1500)).build();
        List<DonorUser> donorUsers = new ArrayList<>();
        donorUsers.add(donorUser_1);
        donorUsers.add(donorUser_2);
        Project project_1 = ProjectBuilder.aProject().withDurationInDays(0).build();
        Project project_2 = ProjectBuilder.aProject().withDurationInDays(0).build();
        List<Project> projects = new ArrayList<>();
        projects.add(project_1);
        projects.add(project_2);
        Manager manager = ManagerBuilder.aManager().withUsers(donorUsers).withOpenProjects(projects).build();

        assertEquals(2, manager.getOpenProjects().size());
        assertEquals(new BigDecimal(1500), donorUser_1.getMoney());
        assertEquals(new BigDecimal(1500), donorUser_2.getMoney());

        donorUser_1.donate(new BigDecimal(500), "Donation", project_1);
        donorUser_2.donate(new BigDecimal(500), "Donation", project_1);
        donorUser_2.donate(new BigDecimal(500), "Donation", project_2);

        assertTrue(project_1.hasReachedGoal());
        assertFalse(project_2.hasReachedGoal());
        assertEquals(new BigDecimal(1000), donorUser_1.getMoney());
        assertEquals(1, donorUser_1.getDonations().size());
        assertEquals(new BigDecimal(500), donorUser_2.getMoney());
        assertEquals(2, donorUser_2.getDonations().size());

        manager.closeFinishedProjects();
        assertEquals(0, manager.getOpenProjects().size());
        assertEquals(2, manager.getClosedProjects().size());
        assertEquals(new BigDecimal(1000), donorUser_1.getMoney());
        assertEquals(1, donorUser_1.getDonations().size());
        assertEquals(new BigDecimal(1000), donorUser_2.getMoney());
        assertEquals(1, donorUser_2.getDonations().size());
    }

    @Test
    public void testManagerTopTenDonations() {
        DonorUser donorUser_1 = mock(DonorUser.class);
        Donation donation_1_1 = DonationBuilder.aDonation().withAmount(new BigDecimal(1200)).build();
        Donation donation_1_2 = DonationBuilder.aDonation().withAmount(new BigDecimal(700)).build();
        Donation donation_1_3 = DonationBuilder.aDonation().withAmount(new BigDecimal(870)).build();
        Donation donation_1_4 = DonationBuilder.aDonation().withAmount(new BigDecimal(3000)).build();
        List<Donation> donations_1 = new ArrayList<>();
        donations_1.add(donation_1_1);
        donations_1.add(donation_1_2);
        donations_1.add(donation_1_3);
        donations_1.add(donation_1_4);
        when(donorUser_1.getDonations()).thenReturn(donations_1);

        DonorUser donorUser_2 = mock(DonorUser.class);
        Donation donation_2_1 = DonationBuilder.aDonation().withAmount(new BigDecimal(450)).build();
        Donation donation_2_2 = DonationBuilder.aDonation().withAmount(new BigDecimal(2100)).build();
        Donation donation_2_3 = DonationBuilder.aDonation().withAmount(new BigDecimal(4200)).build();
        Donation donation_2_4 = DonationBuilder.aDonation().withAmount(new BigDecimal(120)).build();
        List<Donation> donations_2 = new ArrayList<>();
        donations_2.add(donation_2_1);
        donations_2.add(donation_2_2);
        donations_2.add(donation_2_3);
        donations_2.add(donation_2_4);
        when(donorUser_2.getDonations()).thenReturn(donations_2);

        DonorUser donorUser_3 = mock(DonorUser.class);
        Donation donation_3_1 = DonationBuilder.aDonation().withAmount(new BigDecimal(1750)).build();
        Donation donation_3_2 = DonationBuilder.aDonation().withAmount(new BigDecimal(1000)).build();
        Donation donation_3_3 = DonationBuilder.aDonation().withAmount(new BigDecimal(200)).build();
        Donation donation_3_4 = DonationBuilder.aDonation().withAmount(new BigDecimal(3290)).build();
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
        Manager manager = ManagerBuilder.aManager().withUsers(donorUsers).build();

        List<Donation> topTenDonations = manager.getTopTenBiggestDonations();
        assertEquals(10, topTenDonations.size());
    }
    
    @Test
    public void testManagerTopTenDonationsWithNoLocations() {
        Manager manager = ManagerBuilder.aManager().build();

        List<Donation> topTenDonations = manager.getTopTenBiggestDonations();
        assertEquals(0, topTenDonations.size());
    }
    
    @Test
    public void testManagerTopTenDonationStarvedLocationsWithNoLocations() {
        Manager manager = ManagerBuilder.aManager().build();

        List<Location> topTenDonationStarvedLocations = manager.getTopTenDonationStarvedLocations();
        assertEquals(0, topTenDonationStarvedLocations.size());
    }
    
    @Test
    public void testManagerTopTenDonationStarvedLocationsElementsOrder() {
        List<Project> openProjects = new ArrayList<>();
        Project project1 = ProjectBuilder
                .aProject()
                .withStartDate(LocalDate.parse("2020-01-01"))
                .withLocation(LocationBuilder.aLocation().build())
                .withDonations(
                        Stream.of(DonationBuilder
                                .aDonation()
                                .withDate(LocalDate.parse("2020-04-01"))
                                .build()
                        ).collect(Collectors.toList())
                )
                .build();
        Project project2 = ProjectBuilder
                .aProject()
                .withStartDate(LocalDate.parse("2020-02-01"))
                .withLocation(LocationBuilder.aLocation().build())
                .withDonations(
                        Stream.of(DonationBuilder
                                .aDonation()
                                .withDate(LocalDate.parse("2020-02-05"))
                                .build()
                        ).collect(Collectors.toList())
                )
                .build();
        Project project3 = ProjectBuilder
                .aProject()
                .withStartDate(LocalDate.parse("2020-03-01"))
                .withLocation(LocationBuilder.aLocation().build())
                .build();
        openProjects.add(project1);
        openProjects.add(project2);
        openProjects.add(project3);
        
        Manager manager = ManagerBuilder.aManager()
                .withOpenProjects(openProjects)
                .build();

        List<Location> topTenDonationStarvedLocations = manager.getTopTenDonationStarvedLocations();
        assertEquals(project2.getLocation(), topTenDonationStarvedLocations.get(0));
        assertEquals(project3.getLocation(), topTenDonationStarvedLocations.get(1));
        assertEquals(project1.getLocation(), topTenDonationStarvedLocations.get(2));
    }
    
    @Test
    public void testManagerTopTenDonationStarvedLocationsResponseAmmount() {
        List<Project> openProjects = new ArrayList<>();
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-01"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-02"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-03"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-04"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-05"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-06"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-07"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-08"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-09"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-10"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-11"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-12"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        openProjects.add(ProjectBuilder.aProject()
                .withStartDate(LocalDate.parse("2020-01-13"))
                .withLocation(LocationBuilder.aLocation().build())
                .build()
        );
        
        Manager manager = ManagerBuilder.aManager()
                .withOpenProjects(openProjects)
                .build();

        List<Location> topTenDonationStarvedLocations = manager.getTopTenDonationStarvedLocations();
        assertEquals(10, topTenDonationStarvedLocations.size());
    }

}