package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

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
    public void testUserDonation() {
        User user = UserBuilder.aUser().build();
        assertTrue(user.getDonations().isEmpty());

        int amount = 3570;
        String comment = "This is my donation";
        Project project = mock(Project.class);
        user.donate(amount, comment, project);
        assertTrue(!user.getDonations().isEmpty());

        Donation donation = user.getDonations().get(0);
        assertEquals(donation.getAmount(), amount);
        assertEquals(donation.getComment(), comment);
    }

}