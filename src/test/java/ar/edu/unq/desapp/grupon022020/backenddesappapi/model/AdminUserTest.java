package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.AdminUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void testAdminUserLocations() {
        Location location_1 = mock(Location.class);
        Location location_2 = mock(Location.class);
        List<Location> locations = new ArrayList<>();
        locations.add(location_1);
        locations.add(location_2);

        AdminUser adminUser = AdminUserBuilder.anAdminUser().withLocations(locations).build();
        assertEquals(locations, adminUser.getLocations());
        assertTrue(adminUser.getLocations().size() == 2);
    }

    @Test
    public void testAdminUserProjects() {
        Project project_1 = mock(Project.class);
        Project project_2 = mock(Project.class);
        List<Project> projects = new ArrayList<>();
        projects.add(project_1);
        projects.add(project_2);

        AdminUser adminUser = AdminUserBuilder.anAdminUser().withProjects(projects).build();
        assertEquals(projects, adminUser.getProjects());
        assertTrue(adminUser.getProjects().size() == 2);
    }

    @Test
    public void testAdminUserProjectCreation() {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        assertTrue(adminUser.getProjects().size() == 0);

        Location location = mock(Location.class);
        int locationPopulation = 1750;
        when(location.getPopulation()).thenReturn(locationPopulation);

        String projectName = "Conectando Rio Turbio";
        int factor = 50000;
        int closurePercentage = 85;
        LocalDate startDate = LocalDate.parse("2020-12-27");
        LocalDate finishDate = LocalDate.parse("2022-05-04");

        adminUser.createProject(projectName, factor, closurePercentage, startDate, finishDate, location);
        Project newProject = adminUser.getProjects().get(0);

        assertEquals(1, adminUser.getProjects().size());
        assertEquals(projectName, newProject.getName());
        assertEquals(factor, newProject.getFactor());
        assertEquals(closurePercentage, newProject.getClosurePercentage());
        assertEquals(startDate, newProject.getStartDate());
        assertEquals(finishDate, newProject.getFinishDate());
        assertEquals(locationPopulation, newProject.getLocationPopulation());
    }

    @Test
    public void testAdminUserProjectCancellation() throws InvalidProjectOperation {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Location location = mock(Location.class);
        String name = "Conectando Tandil";
        LocalDate startDate = LocalDate.parse("2020-12-27");
        LocalDate finishDate = LocalDate.parse("2022-05-04");
        adminUser.createProject(name, 1000, 60, startDate, finishDate, location);

        Project project = adminUser.getProjects().get(0);
        assertEquals(finishDate, project.getFinishDate());

        adminUser.cancelProject(name);
        assertEquals(startDate.minusDays(1), project.getFinishDate());
    }

    @Test
    public void testAdminUserNonExistentProjectCancellation() {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Location location = mock(Location.class);
        String name = "Mar Chiquita 3.0";
        LocalDate startDate = LocalDate.parse("2020-12-27");
        LocalDate finishDate = LocalDate.parse("2022-05-04");
        adminUser.createProject("Conectando Tandil", 1000, 60, startDate, finishDate, location);

        try
        {
            adminUser.cancelProject(name);
        }
        catch(InvalidProjectOperation e)
        {
            String message = "Project " + name + " does not exists";
            assertEquals(message, e.getMessage());
        }
    }

}