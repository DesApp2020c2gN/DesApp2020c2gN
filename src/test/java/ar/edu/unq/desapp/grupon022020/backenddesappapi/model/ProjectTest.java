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

        assertEquals(project.getName(), name);
    }

    @Test
    public void testProjectFactor() {
        int factor = 1200;
        Project project = ProjectBuilder.aProject().withFactor(factor).build();

        assertEquals(project.getFactor(), factor);
    }

    @Test
    public void testProjectClosurePercentage() {
        int closurePercentage = 75;
        Project project = ProjectBuilder.aProject().withClosurePercentage(closurePercentage).build();

        assertEquals(project.getClosurePercentage(), closurePercentage);
    }

    @Test
    public void testProjectStartDate() {
        LocalDate startDate = LocalDate.parse("2020-09-15");
        Project project = ProjectBuilder.aProject().withStartDate(startDate).build();

        assertEquals(project.getStartDate(), startDate);
    }

    @Test
    public void testProjectFinishDate() {
        LocalDate finishDate = LocalDate.parse("2020-12-01");
        Project project = ProjectBuilder.aProject().withFinishDate(finishDate).build();

        assertEquals(project.getFinishDate(), finishDate);
    }

    @Test
    public void testProjectDonationList() {
        List<Donation> donations = new ArrayList<>();
        Donation donation_1 = mock(Donation.class);
        Donation donation_2 = mock(Donation.class);
        donations.add(donation_1);
        donations.add(donation_2);

        Project project = ProjectBuilder.aProject().withDonations(donations).build();

        assertEquals(project.getDonations(), donations);
    }

    @Test
    public void testProjectLocation() {
        Location location = mock(Location.class);
        Project project = ProjectBuilder.aProject().withLocation(location).build();

        assertEquals(project.getLocation(), location);
    }
}
