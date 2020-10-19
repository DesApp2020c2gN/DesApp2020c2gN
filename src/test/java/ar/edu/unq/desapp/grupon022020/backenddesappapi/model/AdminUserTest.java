package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.AdminUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdminUserTest {
    //TODO: test in this class have to be changed or removed!

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
    public void testAdminUserSystem() {
        Manager manager = mock(Manager.class);
        List<Location> locations = new ArrayList<>();
        List<Project> projects = new ArrayList<>();
        List<DonorUser> users = new ArrayList<>();
        when(manager.getLocations()).thenReturn(locations);
        when(manager.getOpenProjects()).thenReturn(projects);
        when(manager.getUsers()).thenReturn(users);

        AdminUser adminUser = AdminUserBuilder.anAdminUser().withSystem(manager).build();
        assertEquals(locations, adminUser.getLocations());
        assertEquals(projects, adminUser.getOpenProjects());
        assertEquals(users, adminUser.getUsers());
    }


    @Test
    public void testAdminUserProjectCreation() throws InvalidProjectOperationException {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        assertEquals(0, adminUser.getOpenProjects().size());

        Location location = mock(Location.class);
        int locationPopulation = 1750;
        when(location.getPopulation()).thenReturn(locationPopulation);

        String projectName = "Conectando Rio Turbio";
        int factor = 50000;
        int closurePercentage = 85;
        LocalDate startDate = LocalDate.parse("2020-12-27");

        Project newProject = adminUser.createProject(projectName, factor, closurePercentage, startDate, 200, location);

        assertEquals(1, adminUser.getOpenProjects().size());
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
        LocalDate startDate = LocalDate.now().minusDays(12);

        try {
            adminUser.createProject(name, 1000, 60, startDate, 120, location);
        } catch (InvalidProjectOperationException e) {
            String message = "Start day of " + startDate.toString() + " for project " + name + " is not valid";
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
/*
    @Test
    public void testAdminUserProjectCancellation() throws InvalidProjectOperationException {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Location location = mock(Location.class);
        String name = "Conectando Tandil";
        LocalDate startDate = LocalDate.parse("2020-12-27");
        adminUser.createProject(name, 1000, 60, startDate, 200, location);

        Project project = adminUser.getOpenProjects().get(0);
        assertEquals(startDate.plusDays(200), project.getFinishDate());

        adminUser.cancelProject(name);
        assertEquals(startDate.minusDays(1), project.getFinishDate());
    }

    @Test
    public void testAdminUserNonExistentProjectCancellation() throws InvalidProjectOperationException {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Location location = mock(Location.class);
        String name = "Mar Chiquita 3.0";
        LocalDate startDate = LocalDate.parse("2020-12-27");
        adminUser.createProject("Conectando Tandil", 1000, 60, startDate, 200, location);

        try {
            adminUser.cancelProject(name);
        } catch (InvalidProjectOperationException e) {
            String message = "Project " + name + " does not exists";
            assertEquals(message, e.getMessage());
        }
    }
*/
}