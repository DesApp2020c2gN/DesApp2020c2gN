package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.AdminUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminUserTest {

    @Test
    public void testAdminUserName() {
        String name = "Admin";
        AdminUser adminUser = AdminUserBuilder.anAdminUser().withName(name).build();

        assertEquals(name, adminUser.getName());
    }

    @Test
    public void testAdminUserMail() {
        String mail = "admin@admin.com";
        AdminUser adminUser = AdminUserBuilder.anAdminUser().withMail(mail).build();

        assertEquals(mail, adminUser.getMail());
    }

    @Test
    public void testAdminUserPassword() {
        String password = "admin001";
        AdminUser adminUser = AdminUserBuilder.anAdminUser().withPassword(password).build();

        assertEquals(password, adminUser.getPassword());
    }


    @Test
    public void testAdminUserProjectCreation() throws InvalidProjectOperationException {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();

        Location location = mock(Location.class);
        int locationPopulation = 1750;
        when(location.getPopulation()).thenReturn(locationPopulation);

        String projectName = "Conectando Rio Turbio";
        int factor = 50000;
        int closurePercentage = 85;
        LocalDate startDate = LocalDate.now().plusDays(10);

        Project newProject = adminUser.createProject(projectName, factor, closurePercentage, startDate, 200, location);

        assertEquals(projectName, newProject.getName());
        assertEquals(factor, newProject.getFactor());
        assertEquals(closurePercentage, newProject.getClosurePercentage());
        assertEquals(startDate, newProject.getStartDate());
        assertEquals(startDate.plusDays(200), newProject.getFinishDate());
        assertEquals(locationPopulation, newProject.getLocationPopulation());
    }

    @Test
    public void testAdminUserProjectCreationWithInvalidStartDate() {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Location location = mock(Location.class);
        String name = "Mar Chiquita 3.0";
        LocalDate startDate = LocalDate.now().minusDays(10);

        try {
            adminUser.createProject(name, 1000, 60, startDate, 120, location);
        } catch (InvalidProjectOperationException e) {
            String message = "Start day of " + startDate.toString() + " for project " + name + " is not valid";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testAdminUserProjectCreationWithInvalidFactor() {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Location location = mock(Location.class);
        String name = "Santa Clara 3.0";
        LocalDate startDate = LocalDate.now().plusDays(10);
        int invalidFactor = 0;

        try {
            adminUser.createProject(name, invalidFactor, 60, startDate, 120, location);
        } catch (InvalidProjectOperationException e) {
            String message = "Project " + name + " must have a positive factor";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testAdminUserProjectCreationWithInvalidDurationInDays() {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Location location = mock(Location.class);
        String name = "Santa Clara 3.0";
        LocalDate startDate = LocalDate.now().plusDays(10);
        int durationInDays = 0;

        try {
            adminUser.createProject(name, 1000, 60, startDate, durationInDays, location);
        } catch (InvalidProjectOperationException e) {
            String message = "Project " + name + " must have a positive duration";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testAdminUserProjectCreationWithInvalidClosurePercentage() {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Location location = mock(Location.class);
        String name = "Santa Clara 3.0";
        LocalDate startDate = LocalDate.now().plusDays(10);
        int closurePercentage = 132;

        try {
            adminUser.createProject(name, 1000, closurePercentage, startDate, 30, location);
        } catch (InvalidProjectOperationException e) {
            String message = "Project " + name + " must have a percentage between 1 and 100";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testAdminUserAlreadyOpenedProjectCreation() throws InvalidProjectOperationException {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        String project_1_Name = "Conectando Mercedes";
        String project_2_Name = "Conectando Colon";
        int factor = 100;
        int closurePercentage = 85;
        LocalDate startDate = LocalDate.now();
        Location location = LocationBuilder.aLocation().build();

        adminUser.createProject(project_1_Name, factor, closurePercentage, startDate, 3, location);

        try {
            adminUser.createProject(project_2_Name, factor, closurePercentage, startDate, 3, location);
        } catch (InvalidProjectOperationException e) {
            String message = "A project for location " + location.getName() + " is currently open";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testAdminUserProjectCancellation() throws InvalidProjectOperationException, InvalidDonationException {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Location location = mock(Location.class);
        String name = "Conectando Tandil";
        LocalDate startDate = LocalDate.parse("2020-12-27");
        DonorUser donor_1 = DonorUserBuilder.aDonorUser().withMoney(BigDecimal.valueOf(1000)).build();
        DonorUser donor_2 = DonorUserBuilder.aDonorUser().withMoney(BigDecimal.valueOf(1000)).build();
        List<DonorUser> donorsList = new ArrayList<>();
        donorsList.add(donor_1); donorsList.add(donor_2);
        Project project = adminUser.createProject(name, 1000, 60, startDate, 200, location);
        project.setStartDate(LocalDate.now().minusDays(2));
        donor_1.donate(BigDecimal.valueOf(100), "Donation 1", project);
        assertEquals(startDate.plusDays(200), project.getFinishDate());

        adminUser.cancelProject(project, donorsList);
        assertEquals(LocalDate.now().minusDays(3), project.getFinishDate());
        assertEquals(0, project.getDonations().size());
    }

}