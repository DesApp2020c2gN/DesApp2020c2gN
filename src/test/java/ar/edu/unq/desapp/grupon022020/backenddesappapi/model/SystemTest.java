package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SystemTest {

    @Test
    public void testSystemTopTenDonations() {
        User user_1 = mock(User.class);
        Donation donation_1_1 = DonationBuilder.aDonation().withAmount(1200).build();
        Donation donation_1_2 = DonationBuilder.aDonation().withAmount(700).build();
        Donation donation_1_3 = DonationBuilder.aDonation().withAmount(870).build();
        Donation donation_1_4 = DonationBuilder.aDonation().withAmount(3000).build();
        List<Donation> donations_1 = new ArrayList<>();
        donations_1.add(donation_1_1);
        donations_1.add(donation_1_2);
        donations_1.add(donation_1_3);
        donations_1.add(donation_1_4);
        when(user_1.getDonations()).thenReturn(donations_1);

        User user_2 = mock(User.class);
        Donation donation_2_1 = DonationBuilder.aDonation().withAmount(450).build();
        Donation donation_2_2 = DonationBuilder.aDonation().withAmount(2100).build();
        Donation donation_2_3 = DonationBuilder.aDonation().withAmount(4200).build();
        Donation donation_2_4 = DonationBuilder.aDonation().withAmount(120).build();
        List<Donation> donations_2 = new ArrayList<>();
        donations_2.add(donation_2_1);
        donations_2.add(donation_2_2);
        donations_2.add(donation_2_3);
        donations_2.add(donation_2_4);
        when(user_2.getDonations()).thenReturn(donations_2);

        User user_3 = mock(User.class);
        Donation donation_3_1 = DonationBuilder.aDonation().withAmount(1750).build();
        Donation donation_3_2 = DonationBuilder.aDonation().withAmount(1000).build();
        Donation donation_3_3 = DonationBuilder.aDonation().withAmount(200).build();
        Donation donation_3_4 = DonationBuilder.aDonation().withAmount(3290).build();
        List<Donation> donations_3 = new ArrayList<>();
        donations_3.add(donation_3_1);
        donations_3.add(donation_3_2);
        donations_3.add(donation_3_3);
        donations_3.add(donation_3_4);
        when(user_3.getDonations()).thenReturn(donations_3);

        List<User> users = new ArrayList<>();
        users.add(user_1);
        users.add(user_2);
        users.add(user_3);
        System system = new System(null, users);

        List<Donation> topTenDonations = system.getTopTenBiggestDonations();
        assertTrue(topTenDonations.size() == 10);
    }

}