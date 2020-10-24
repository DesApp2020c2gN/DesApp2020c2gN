package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.ProjectStatus;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.DonationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.ProjectRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DonationServiceTest {

    @InjectMocks
    private DonationService donationService;

    @Mock
    private DonationRepository donationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProjectRepository projectRepository;

    @Test
    public void testDonationServiceFindAll() {
        MockitoAnnotations.initMocks(this);
        List<Donation> donations = new ArrayList<>();
        donations.add(DonationBuilder.aDonation().withDonorNickname("juan123").build());
        donations.add(DonationBuilder.aDonation().withDonorNickname("maria456").build());
        when(donationRepository.findAll()).thenReturn(donations);
        List<Donation> retrievedDonations = donationService.findAll();
        assertEquals(donations, retrievedDonations);
        assertEquals(2, retrievedDonations.size());
    }

    @Test
    public void testDonationServiceFindById() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        Integer id = 7;
        Donation donation = DonationBuilder.aDonation().build();
        donation.setId(id);
        when(donationRepository.existsById(id)).thenReturn(true);
        when(donationRepository.findById(id)).thenReturn(Optional.of(donation));
        assertEquals(donation, donationService.findById(id));
    }

    @Test
    public void testDonationServiceFindByIdForNonExistingDonation() {
        MockitoAnnotations.initMocks(this);
        Integer id = 7;
        when(donationRepository.existsById(id)).thenReturn(false);
        try {
            donationService.findById(id);
        } catch (DataNotFoundException e) {
            String message = "Donation " + id + " does not exists";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testDonationServiceDonate() throws InvalidDonationException, DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).withMoney(BigDecimal.valueOf(9999)).build();
        String projectName = "Conectando Cruz Azul";
        Project project = ProjectBuilder.aProject().withName(projectName).build();
        String comment = "Comentario de test";
        int amount = 1200;
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(projectRepository.existsById(projectName)).thenReturn(true);
        when(userRepository.findById(nickname)).thenReturn(Optional.of(donor));
        when(projectRepository.findById(projectName)).thenReturn(Optional.of(project));
        when(donationRepository.save(any())).thenReturn(null);
        Donation createdDonation = donationService.donate(nickname, projectName, comment, amount);
        assertEquals(nickname, createdDonation.getDonorNickname());
        assertEquals(projectName, createdDonation.getProjectName());
        assertEquals(comment, createdDonation.getComment());
        assertEquals(amount, createdDonation.getAmount().intValue());
    }

    @Test
    public void testDonationServiceDonateForNonExistingDonor() throws InvalidDonationException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        String projectName = "Conectando Cruz Azul";
        String comment = "Comentario de test";
        int amount = 1200;
        when(userRepository.existsById(nickname)).thenReturn(false);
        try {
            donationService.donate(nickname, projectName, comment, amount);
        } catch (DataNotFoundException e) {
            String message = "User " + nickname + " does not exist";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testDonationServiceDonateForNonExistingProject() throws InvalidDonationException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        String projectName = "Conectando Cruz Azul";
        String comment = "Comentario de test";
        int amount = 1200;
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(projectRepository.existsById(projectName)).thenReturn(false);
        try {
            donationService.donate(nickname, projectName, comment, amount);
        } catch (DataNotFoundException e) {
            String message = "Project " + projectName + " does not exist";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testDonationServiceDonateWithInsufficientMoney() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).withMoney(BigDecimal.valueOf(700)).build();
        String projectName = "Conectando Cruz Azul";
        Project project = ProjectBuilder.aProject().withName(projectName).build();
        String comment = "Comentario de test";
        int amount = 1200;
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(projectRepository.existsById(projectName)).thenReturn(true);
        when(userRepository.findById(nickname)).thenReturn(Optional.of(donor));
        when(projectRepository.findById(projectName)).thenReturn(Optional.of(project));
        try {
            donationService.donate(nickname, projectName, comment, amount);
        } catch (InvalidDonationException e) {
            String message = "User " + nickname + " does not have enough money";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testDonationServiceDonateToProjectNotActive() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).withMoney(BigDecimal.valueOf(9999)).build();
        String projectName = "Conectando Cruz Azul";
        String status = ProjectStatus.COMPLETE.name();
        Project project = ProjectBuilder.aProject().withName(projectName).withStatus(status).build();
        String comment = "Comentario de test";
        int amount = 1200;
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(projectRepository.existsById(projectName)).thenReturn(true);
        when(userRepository.findById(nickname)).thenReturn(Optional.of(donor));
        when(projectRepository.findById(projectName)).thenReturn(Optional.of(project));
        try {
            donationService.donate(nickname, projectName, comment, amount);
        } catch (InvalidDonationException e) {
            String message = "Project " + projectName + " is " + status;
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testDonationServiceDonateToProjectNotStarted() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).withMoney(BigDecimal.valueOf(9999)).build();
        String projectName = "Conectando Cruz Azul";
        Project project = ProjectBuilder.aProject().withName(projectName).withStartDate(LocalDate.now().plusDays(7)).build();
        String comment = "Comentario de test";
        int amount = 1200;
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(projectRepository.existsById(projectName)).thenReturn(true);
        when(userRepository.findById(nickname)).thenReturn(Optional.of(donor));
        when(projectRepository.findById(projectName)).thenReturn(Optional.of(project));
        try {
            donationService.donate(nickname, projectName, comment, amount);
        } catch (InvalidDonationException e) {
            String message = "Project " + projectName + " has not started";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testDonationServiceDonateToProjectAlreadyFinished() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).withMoney(BigDecimal.valueOf(9999)).build();
        String projectName = "Conectando Cruz Azul";
        Project project = ProjectBuilder.aProject().withName(projectName).withStartDate(LocalDate.now().minusDays(10)).withDurationInDays(3).build();
        String comment = "Comentario de test";
        int amount = 1200;
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(projectRepository.existsById(projectName)).thenReturn(true);
        when(userRepository.findById(nickname)).thenReturn(Optional.of(donor));
        when(projectRepository.findById(projectName)).thenReturn(Optional.of(project));
        try {
            donationService.donate(nickname, projectName, comment, amount);
        } catch (InvalidDonationException e) {
            String message = "Project " + projectName + " has finished";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testDonationServiceGetTopTenBiggestDonations() {
        MockitoAnnotations.initMocks(this);
        Donation donation_1 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(9000)).build();
        Donation donation_2 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(1230)).build();
        Donation donation_3 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(7650)).build();
        Donation donation_4 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(400)).build();
        Donation donation_5 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(2380)).build();
        Donation donation_6 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(1100)).build();
        Donation donation_7 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(200)).build();
        Donation donation_8 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(115)).build();
        Donation donation_9 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(8760)).build();
        Donation donation_10 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(50)).build();
        Donation donation_11 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(5700)).build();
        Donation donation_12 = DonationBuilder.aDonation().withAmount(BigDecimal.valueOf(65)).build();
        List<Donation> donationList = new ArrayList<>();
        donationList.add(donation_1); donationList.add(donation_2); donationList.add(donation_3);
        donationList.add(donation_4); donationList.add(donation_5); donationList.add(donation_6);
        donationList.add(donation_7); donationList.add(donation_8); donationList.add(donation_9);
        donationList.add(donation_10); donationList.add(donation_11); donationList.add(donation_12);
        when(donationRepository.findAll()).thenReturn(donationList);
        List<Donation> topTenDonations = donationService.getTopTenBiggestDonations();
        assertEquals(10, topTenDonations.size());
        assertEquals(donation_1, topTenDonations.get(0));
        assertEquals(donation_9, topTenDonations.get(1));
        assertEquals(donation_3, topTenDonations.get(2));
        assertEquals(donation_11, topTenDonations.get(3));
        assertEquals(donation_5, topTenDonations.get(4));
        assertFalse(topTenDonations.contains(donation_10));
        assertFalse(topTenDonations.contains(donation_12));
    }

}
