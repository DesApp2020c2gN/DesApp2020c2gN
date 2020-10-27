package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.LoginException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @Test
    public void testUserControllerAllDonorUsersStatus() {
        List<Donor> donors = new ArrayList<>();
        donors.add(DonorBuilder.aDonorUser().withNickname("juan123").build());
        when(userService.findAll()).thenReturn(donors);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.allDonorUsers();
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testUserControllerAllDonorUsersContent() {
        List<Donor> donors = new ArrayList<>();
        donors.add(DonorBuilder.aDonorUser().withNickname("juan123").build());
        when(userService.findAll()).thenReturn(donors);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.allDonorUsers();
        assertNotNull(httpResponse.getBody());
        assertEquals(donors, httpResponse.getBody());
    }

    @Test
    public void testUserControllerGetDonorUserStatus() throws DataNotFoundException {
        String nickname = "maria456";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).build();
        when(userService.findById(nickname)).thenReturn(donor);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.getDonorUser(nickname);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testUserControllerGetDonorUserContent() throws DataNotFoundException {
        String nickname = "maria456";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).build();
        when(userService.findById(nickname)).thenReturn(donor);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.getDonorUser(nickname);
        assertNotNull(httpResponse.getBody());
        assertEquals(donor, httpResponse.getBody());
    }

    @Test
    public void testUserControllerGetDonorUserException() throws DataNotFoundException {
        String nickname = "maria456";
        String message = "User " + nickname + " does not exists";
        doThrow(new DataNotFoundException(message)).when(userService).findById(nickname);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.getDonorUser(nickname);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("User could not be found: " + message, httpResponse.getBody());
    }

    @Test
    public void testUserControllerLoginUserStatus() {
        String nickname = "oscar777";
        String password = "777";
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.loginUser(nickname, password);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testUserControllerLoginUserContent() {
        String nickname = "oscar777";
        String password = "777";
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.loginUser(nickname, password);
        assertNotNull(httpResponse.getBody());
        assertEquals("Donor login successful", httpResponse.getBody());
    }

    @Test
    public void testUserControllerLoginUserException() throws LoginException {
        String nickname = "oscar777";
        String password = "777";
        String message = "Password is incorrect";
        doThrow(new LoginException(message)).when(userService).loginDonorUser(nickname, password);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.loginUser(nickname, password);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("Donor login failed: " + message, httpResponse.getBody());
    }

    @Test
    public void testUserControllerLoginAdminStatus() {
        String nickname = "admin";
        String password = "admin";
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.loginAdmin(nickname, password);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testUserControllerLoginAdminContent() {
        String nickname = "admin";
        String password = "admin";
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.loginAdmin(nickname, password);
        assertNotNull(httpResponse.getBody());
        assertEquals("Admin login successful", httpResponse.getBody());
    }

    @Test
    public void testUserControllerLoginAdminException() throws LoginException {
        String nickname = "admin";
        String password = "admin";
        String message = "Password is incorrect";
        doThrow(new LoginException(message)).when(userService).loginAdmin(nickname, password);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.loginAdmin(nickname, password);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("Admin login failed: " + message, httpResponse.getBody());
    }

    @Test
    public void testUserControllerCreateDonorUserStatus() {
        String nickname = "juan123";
        String mail = "juan@mail.com";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).withMail(mail).build();
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.createDonorUser(donor);
        assertEquals(HttpStatus.CREATED, httpResponse.getStatusCode());
    }

    @Test
    public void testUserControllerCreateDonorUserContent() {
        String nickname = "juan123";
        String mail = "juan@mail.com";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).withMail(mail).build();
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.createDonorUser(donor);
        assertNotNull(httpResponse.getBody());
        assertEquals(donor, httpResponse.getBody());
    }

    @Test
    public void testUserControllerCreateDonorUserException() throws DataNotFoundException {
        String nickname = "juan123";
        String mail = "juan@mail.com";
        String message = "User " + nickname + " already exists";
        Donor donor = DonorBuilder.aDonorUser().withNickname(nickname).withMail(mail).build();
        doThrow(new DataNotFoundException(message)).when(userService).createDonorUser(donor);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) userController.createDonorUser(donor);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("User could not be created: " + message, httpResponse.getBody());
    }

}
