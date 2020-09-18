package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class ProjectTest {

    @Test
    public void testProjectName() {
        String name = "Conectar San Juan";
        Project project = ProjectBuilder.aProject().withName(name).build();

        assertEquals(name, project.getName());
    }

    @Test
    public void testProjectFactor() {
        int factor = 1200;
        Project project = ProjectBuilder.aProject().withFactor(factor).build();

        assertEquals(factor, project.getFactor());
    }

    @Test
    public void testProjectClosurePercentage() {
        int closurePercentage = 75;
        Project project = ProjectBuilder.aProject().withClosurePercentage(closurePercentage).build();

        assertEquals(closurePercentage, project.getClosurePercentage());
    }

    @Test
    public void testProjectStartDate() {
        LocalDate startDate = LocalDate.parse("2020-09-15");
        Project project = ProjectBuilder.aProject().withStartDate(startDate).build();

        assertEquals(startDate, project.getStartDate());
    }

    @Test
    public void testProjectFinishDate() {
        LocalDate finishDate = LocalDate.parse("2020-12-01");
        Project project = ProjectBuilder.aProject().withFinishDate(finishDate).build();

        assertEquals(finishDate, project.getFinishDate());
    }

    @Test
    public void testProjectDonationList() {
        List<Donation> donations = new ArrayList<>();
        Donation donation_1 = mock(Donation.class);
        Donation donation_2 = mock(Donation.class);
        donations.add(donation_1);
        donations.add(donation_2);

        Project project = ProjectBuilder.aProject().withDonations(donations).build();

        assertEquals(donations, project.getDonations());
    }

    @Test
    public void testProjectLocation() {
        Location location = mock(Location.class);
        Project project = ProjectBuilder.aProject().withLocation(location).build();

        assertEquals(location, project.getLocation());
    }
}
