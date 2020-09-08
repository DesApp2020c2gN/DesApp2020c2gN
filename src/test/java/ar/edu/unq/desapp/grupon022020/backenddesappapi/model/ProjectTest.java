package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
