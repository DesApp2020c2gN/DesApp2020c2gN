package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.DonationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(DonationController.class)
public class DonationControllerTest {

    @MockBean
    private DonationService donationService;

    @Autowired
    private DonationController donationController;

    @Test
    public void testDonationControllerAllDonationsStatus() {
        List<Donation> donations = new ArrayList<>();
        donations.add(DonationBuilder.aDonation().build());
        when(donationService.findAll()).thenReturn(donations);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) donationController.allDonations();
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testDonationControllerAllDonationsContent() {
        List<Donation> donations = new ArrayList<>();
        donations.add(DonationBuilder.aDonation().build());
        when(donationService.findAll()).thenReturn(donations);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) donationController.allDonations();
        assertNotNull(httpResponse.getBody());
        assertEquals(donations, httpResponse.getBody());
    }

    @Test
    public void testDonationControllerDonateStatus() throws InvalidDonationException, DataNotFoundException {
        String nickname = "maria456";
        String projectName = "Conectando Santa Rita";
        String comment = "First donation";
        int value = 1230;
        BigDecimal amount = BigDecimal.valueOf(value);
        Donation donation = DonationBuilder.aDonation().withDonorNickname(nickname).withProjectName(projectName).withComment(comment).withAmount(amount).build();
        when(donationService.donate(nickname, projectName, comment, value)).thenReturn(donation);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) donationController.donate(nickname, projectName, comment, value);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testDonationControllerDonateContent() throws InvalidDonationException, DataNotFoundException {
        String nickname = "maria456";
        String projectName = "Conectando Mercedes";
        String comment = "My donation";
        int value = 1230;
        BigDecimal amount = BigDecimal.valueOf(value);
        Donation donation = DonationBuilder.aDonation().withDonorNickname(nickname).withProjectName(projectName).withComment(comment).withAmount(amount).build();
        when(donationService.donate(nickname, projectName, comment, value)).thenReturn(donation);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) donationController.donate(nickname, projectName, comment, value);
        assertNotNull(httpResponse.getBody());
        assertEquals(donation, httpResponse.getBody());
    }

    @Test
    public void testDonationControllerDonateException() throws InvalidDonationException, DataNotFoundException {
        String nickname = "maria456";
        String projectName = "Conectando Santa Rita";
        String comment = "First donation";
        int value = 1230;
        String message = "User " + nickname + " does not exist";
        doThrow(new DataNotFoundException(message)).when(donationService).donate(nickname, projectName, comment, value);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) donationController.donate(nickname, projectName, comment, value);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("Donation could not be completed: " + message, httpResponse.getBody());
    }

}
