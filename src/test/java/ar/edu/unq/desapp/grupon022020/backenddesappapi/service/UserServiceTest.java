package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.LoginException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testUserServiceFindAll() {
        MockitoAnnotations.initMocks(this);
        List<Donor> donors = new ArrayList<>();
        donors.add(DonorBuilder.aDonorUser().withNickname("juan123").build());
        donors.add(DonorBuilder.aDonorUser().withNickname("maria456").build());
        when(userRepository.findAll()).thenReturn(donors);
        List<Donor> retrievedDonors = userService.findAll();
        assertEquals(donors, retrievedDonors);
        assertEquals(2, retrievedDonors.size());
    }

    @Test
    public void testUserServiceFindById() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).build();
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(userRepository.findById(nickname)).thenReturn(Optional.of(donor));
        assertEquals(donor, userService.findById(nickname));
    }

    @Test
    public void testUserServiceFindByIdForNonExistingUser() {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).build();
        when(userRepository.existsById(nickname)).thenReturn(false);
        try {
            assertEquals(donor, userService.findById(nickname));
        } catch (DataNotFoundException e) {
            String message = "User " + nickname + " does not exists";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserServiceCreateDonorUser() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String nickname = "maria456";
        String name = "Maria";
        String mail = "maria@mail.com";
        String password = "456";
        int money = 1570;
        when(userRepository.existsById(nickname)).thenReturn(false);
        Donor donor = DonorBuilder.aDonorUser().
                withNickname(nickname).
                withName(name).
                withMail(mail).
                withPassword(password).
                withMoney(BigDecimal.valueOf(money)).
                build();
        userService.createDonorUser(donor);
        assertEquals(nickname, donor.getNickname());
        assertEquals(name, donor.getName());
        assertEquals(mail, donor.getMail());
        assertEquals(password, donor.getPassword());
        assertEquals(BigDecimal.valueOf(money), donor.getMoney());
        assertEquals(0, donor.getPoints());
    }

    @Test
    public void testUserServiceCreateDonorUserForAlreadyExistingUser() {
        MockitoAnnotations.initMocks(this);
        String nickname = "maria456";
        String name = "Maria";
        String mail = "maria@mail.com";
        String password = "456";
        int money = 1570;
        when(userRepository.existsById(nickname)).thenReturn(true);
        Donor donor = DonorBuilder.aDonorUser().
                withNickname(nickname).
                withName(name).
                withMail(mail).
                withPassword(password).
                withMoney(BigDecimal.valueOf(money)).
                build();
        try {
            userService.createDonorUser(donor);
        } catch (DataNotFoundException e) {
            String message = "User " + nickname + " already exists";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserServiceLoginDonorUser() {
        MockitoAnnotations.initMocks(this);
        String nickname = "maria456";
        String password = "456";
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(userRepository.loginUser(nickname, password)).thenReturn(true);
        assertDoesNotThrow(() -> userService.loginDonorUser(nickname, password));
    }

    @Test
    public void testUserServiceLoginDonorUserForWrongNickname() {
        MockitoAnnotations.initMocks(this);
        String nickname = "maria456";
        String password = "456";
        when(userRepository.existsById(nickname)).thenReturn(false);
        when(userRepository.loginUser(nickname, password)).thenReturn(true);
        try {
            userService.loginDonorUser(nickname, password);
        } catch (LoginException e) {
            String message = "Nickname belongs to a non existing user";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserServiceLoginDonorUserForWrongPassword() {
        MockitoAnnotations.initMocks(this);
        String nickname = "maria456";
        String password = "456";
        when(userRepository.existsById(nickname)).thenReturn(true);
        when(userRepository.loginUser(nickname, password)).thenReturn(false);
        try {
            userService.loginDonorUser(nickname, password);
        } catch (LoginException e) {
            String message = "Password is incorrect";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testUserLoginByMail() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String nickname = "juan123";
        String mail = "juan@mail.com";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).withMail(mail).build();
        when(userRepository.findAll()).thenReturn(new ArrayList<Donor>(Arrays.asList(donor)));
        assertDoesNotThrow(() -> userService.loginByMail(mail));
    }

}
