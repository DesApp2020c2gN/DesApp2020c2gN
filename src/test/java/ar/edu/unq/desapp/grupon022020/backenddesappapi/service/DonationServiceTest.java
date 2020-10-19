package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    public void testDonationServiceFindById() {
        MockitoAnnotations.initMocks(this);
        Integer id = 7;
        Donation donation = DonationBuilder.aDonation().build();
        donation.setId(id);
        when(donationRepository.findById(id)).thenReturn(Optional.of(donation));
        assertEquals(donation, donationService.findById(id));
    }

    @Test
    public void testDonationServiceDonate() throws InvalidDonationException, DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        DonorUser donorUser = DonorUserBuilder.aDonorUser().withNickname(nickname).withMoney(BigDecimal.valueOf(9999)).build();
        String projectName = "Conectando Cruz Azul";
        Project project = ProjectBuilder.aProject().withName(projectName).build();
        String comment = "Comentario de test";
        int amount = 1200;
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(projectRepository.existsById(projectName)).thenReturn(true);
        when(userRepository.findById(nickname)).thenReturn(Optional.of(donorUser));
        when(projectRepository.findById(projectName)).thenReturn(Optional.of(project));
        when(donationRepository.save(any())).thenReturn(null);
        Donation createdDonation = donationService.donate(nickname, projectName, comment, amount);
        assertEquals(nickname, createdDonation.getDonorNickname());
        assertEquals(projectName, createdDonation.getProjectName());
        assertEquals(comment, createdDonation.getComment());
        assertEquals(amount, createdDonation.getAmount().intValue());
    }
}
