package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.ProjectClosedException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserTest {

    @Test
    public void testUserName() {
        String name = "Marcelo";
        User user = UserBuilder.aUser().withName(name).build();

        assertEquals(user.getName(), name);
    }

    @Test
    public void testUserNickname() {
        String nickname = "Marce98";
        User user = UserBuilder.aUser().withNickname(nickname).build();

        assertEquals(user.getNickname(), nickname);
    }

    @Test
    public void testUserMail() {
        String mail = "marce98@gmail.com";
        User user = UserBuilder.aUser().withMail(mail).build();

        assertEquals(user.getMail(), mail);
    }

    @Test
    public void testUserPassword() {
        String password = "marce_98_1001";
        User user = UserBuilder.aUser().withPassword(password).build();

        assertEquals(user.getPassword(), password);
    }

    @Test
    public void testUserDonationList() {
        List<Donation> donations = new ArrayList<>();
        Donation donation_1 = mock(Donation.class);
        Donation donation_2 = mock(Donation.class);
        donations.add(donation_1);
        donations.add(donation_2);

        User user = UserBuilder.aUser().withDonations(donations).build();

        assertEquals(user.getDonations(), donations);
    }

    @Test
    public void testUserPoints() {
        int points = 71;
        User user = UserBuilder.aUser().withPoints(points).build();

        assertEquals(user.getPoints(), points);
    }

    @Test
    public void testUserDonation() throws ProjectClosedException {
        User user = UserBuilder.aUser().build();
        assertTrue(user.getDonations().isEmpty());

        int amount = 3570;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        user.donate(amount, comment, project);
        assertFalse(user.getDonations().isEmpty());

        Donation donation = user.getDonations().get(0);
        assertEquals(donation.getAmount(), amount);
        assertEquals(donation.getComment(), comment);
    }

    @Test
    public void testUserPointsWithLess1000AmountAndPlus2000Population() throws ProjectClosedException {
        User user = UserBuilder.aUser().build();
        assertEquals(user.getPoints(), 0);

        int donationAmount = 500;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        user.donate(donationAmount, comment, project);

        assertEquals(user.getPoints(), 0);
    }

    @Test
    public void testUserPointsWithPlus1000AmountAndPlus2000Population() throws ProjectClosedException {
        User user = UserBuilder.aUser().build();
        assertEquals(user.getPoints(), 0);

        int donationAmount = 2000;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3000);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        user.donate(donationAmount, comment, project);

        assertEquals(user.getPoints(), donationAmount);
    }

    @Test
    public void testUserPointsWithPlus1000AmountAndLess2000Population() throws ProjectClosedException {
        User user = UserBuilder.aUser().build();
        assertEquals(user.getPoints(), 0);

        int donationAmount = 2000;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(1700);
        when(project.getFinishDate()).thenReturn(LocalDate.now());
        user.donate(donationAmount, comment, project);

        assertEquals(user.getPoints(), donationAmount * 2);
    }

    @Test
    public void testUserPointsWithLastDonationOnSameMonth() throws ProjectClosedException {
        User user = UserBuilder.aUser().build();
        assertEquals(user.getPoints(), 0);

        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3300);
        when(project.getFinishDate()).thenReturn(LocalDate.now());

        user.donate(500, "First donation", project);
        assertEquals(user.getPoints(), 0);
        assertEquals(user.getDonations().size(), 1);

        int donationAmount = 2500;
        String comment = "This is my donation";
        user.donate(donationAmount, comment, project);
        assertEquals(user.getPoints(), donationAmount + 500);
    }
    
    @Test
    public void testUserDonatesOnClosedProject() throws ProjectClosedException {
        User user = UserBuilder.aUser().build();
        
        Project project = mock(Project.class);
        when(project.getLocationPopulation()).thenReturn(3300);
        when(project.getFinishDate()).thenReturn(LocalDate.now().plusDays(-1));

        assertThrows(ProjectClosedException.class, () -> {
            user.donate(500, "First donation", project);
        });
    }

}