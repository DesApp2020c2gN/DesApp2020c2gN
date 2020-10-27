package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ProjectController projectController;

    @Test
    public void testProjectControllerAllProjectsStatus() {
        List<Project> projects = new ArrayList<>();
        projects.add(ProjectBuilder.aProject().withName("Conectando Santa Clara").build());
        when(projectService.findAll()).thenReturn(projects);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.allProjects();
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testProjectControllerAllProjectsContent() {
        List<Project> projects = new ArrayList<>();
        projects.add(ProjectBuilder.aProject().withName("Conectando Santa Clara").build());
        when(projectService.findAll()).thenReturn(projects);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.allProjects();
        assertNotNull(httpResponse.getBody());
        assertEquals(projects, httpResponse.getBody());
    }

    @Test
    public void testProjectControllerGetProjectStatus() throws DataNotFoundException {
        String projectName = "Conectando Santa Clara";
        Project project = ProjectBuilder.aProject().withName(projectName).build();
        when(projectService.findById(projectName)).thenReturn(project);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.getProject(projectName);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testProjectControllerGetProjectContent() throws DataNotFoundException {
        String projectName = "Conectando Santa Clara";
        Project project = ProjectBuilder.aProject().withName(projectName).build();
        when(projectService.findById(projectName)).thenReturn(project);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.getProject(projectName);
        assertNotNull(httpResponse.getBody());
        assertEquals(project, httpResponse.getBody());
    }

    @Test
    public void testProjectControllerGetProjectException() throws DataNotFoundException {
        String projectName = "Conectando Santa Clara";
        Project project = ProjectBuilder.aProject().withName(projectName).build();
        String message = "Project " + projectName + " does not exists";
        doThrow(new DataNotFoundException(message)).when(projectService).findById(projectName);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.getProject(projectName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("Project could not be found: " + message, httpResponse.getBody());
    }

    @Test
    public void testProjectControllerCreateProjectStatus() throws DataNotFoundException, InvalidProjectOperationException {
        String projectName = "Conectando Santa Clara";
        int factor = 20;
        int closurePercentage = 75;
        String startDate = "2020-12-15";
        int durationInDays = 40;
        String locationName = "Conectando Tandil";
        Project project = ProjectBuilder.aProject().withName(projectName)
                .withFactor(factor)
                .withClosurePercentage(closurePercentage)
                .withStartDate(LocalDate.parse(startDate))
                .withDurationInDays(durationInDays)
                .withLocation(LocationBuilder.aLocation().withName(locationName).build())
                .build();
        when(projectService.createProject(projectName, factor, closurePercentage, startDate, durationInDays, locationName)).thenReturn(project);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.createProject(projectName, factor, closurePercentage, startDate, durationInDays, locationName);
        assertEquals(HttpStatus.CREATED, httpResponse.getStatusCode());
    }

    @Test
    public void testProjectControllerCreateProjectContent() throws DataNotFoundException, InvalidProjectOperationException {
        String projectName = "Conectando Santa Clara";
        int factor = 20;
        int closurePercentage = 75;
        String startDate = "2020-12-15";
        int durationInDays = 40;
        String locationName = "Conectando Tandil";
        Project project = ProjectBuilder.aProject().withName(projectName)
                .withFactor(factor)
                .withClosurePercentage(closurePercentage)
                .withStartDate(LocalDate.parse(startDate))
                .withDurationInDays(durationInDays)
                .withLocation(LocationBuilder.aLocation().withName(locationName).build())
                .build();
        when(projectService.createProject(projectName, factor, closurePercentage, startDate, durationInDays, locationName)).thenReturn(project);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.createProject(projectName, factor, closurePercentage, startDate, durationInDays, locationName);
        assertNotNull(httpResponse.getBody());
        assertEquals(project, httpResponse.getBody());
    }

    @Test
    public void testProjectControllerCreateProjectException() throws DataNotFoundException, InvalidProjectOperationException {
        String projectName = "Conectando Santa Clara";
        int factor = 20;
        int closurePercentage = 75;
        String startDate = "2020-12-15";
        int durationInDays = 40;
        String locationName = "Conectando Tandil";
        String message = "There is no location with name " + locationName;
        doThrow(new DataNotFoundException(message)).when(projectService).createProject(projectName, factor, closurePercentage, startDate, durationInDays, locationName);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.createProject(projectName, factor, closurePercentage, startDate, durationInDays, locationName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("Project could not be created: " + message, httpResponse.getBody());
    }

    @Test
    public void testProjectControllerCancelProjectStatus() {
        String projectName = "Conectando Santa Clara";
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.cancelProject(projectName);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testProjectControllerCancelProjectContent() {
        String projectName = "Conectando Santa Clara";
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.cancelProject(projectName);
        assertNotNull(httpResponse.getBody());
        assertEquals("Project " + projectName + " cancelled", httpResponse.getBody());
    }

    @Test
    public void testProjectControllerCancelProjectException() throws InvalidProjectOperationException, DataNotFoundException {
        String projectName = "Conectando Santa Clara";
        String message = "Project " + projectName + " does not exists";
        doThrow(new DataNotFoundException(message)).when(projectService).cancelProject(projectName);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) projectController.cancelProject(projectName);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("Project could not be cancelled: " + message, httpResponse.getBody());
    }

}
