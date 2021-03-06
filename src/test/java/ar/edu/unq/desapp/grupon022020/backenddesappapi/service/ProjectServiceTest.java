package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.ProjectStatus;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.DonationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.LocationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.ProjectRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceTest {

    @InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private LocationRepository locationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private DonationRepository donationRepository;
    @Mock
    private DonationService donationService;

    @Test
    public void testProjectServiceFindAll() {
        MockitoAnnotations.initMocks(this);
        List<Project> projects = new ArrayList<>();
        projects.add(ProjectBuilder.aProject().withName("Conectando Santa Rita").build());
        projects.add(ProjectBuilder.aProject().withName("Conectando Cruz Azul").build());
        when(projectRepository.findAll()).thenReturn(projects);
        List<Project> recoveredProjects = projectService.findAll();
        assertEquals(projects, recoveredProjects);
        assertEquals(2, recoveredProjects.size());
    }

    @Test
    public void testProjectServiceFindById() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Santa Rita";
        Project project = ProjectBuilder.aProject().withName(name).build();
        when(projectRepository.existsById(name)).thenReturn(true);
        when(projectRepository.findById(name)).thenReturn(Optional.of(project));
        assertEquals(project, projectService.findById(name));
    }

    @Test
    public void testProjectServiceFindByIdForNonExistingProject() {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Santa Rita";
        when(projectRepository.existsById(name)).thenReturn(false);
        try {
            projectService.findById(name);
        } catch (DataNotFoundException e) {
            String message = "Project " + name + " does not exists";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceCreateProject() throws InvalidProjectOperationException, DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Rio Turbio";
        int factor = 1300;
        int closurePercentage = 75;
        String startDate = LocalDate.now().plusDays(10).toString();
        int durationInDays = 60;
        String locationName = "Rio Turbio";
        Location location = LocationBuilder.aLocation().withName(locationName).build();
        when(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.ACTIVE.name())).thenReturn(false);
        when(locationRepository.existsById(locationName)).thenReturn(true);
        when(locationRepository.findById(locationName)).thenReturn(Optional.of(location));
        when(projectRepository.save(any())).thenReturn(null);
        Project createdProject = projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
        assertEquals(name, createdProject.getName());
        assertEquals(factor, createdProject.getFactor());
        assertEquals(closurePercentage, createdProject.getClosurePercentage());
        assertEquals(LocalDate.now().plusDays(10), createdProject.getStartDate());
        assertEquals(LocalDate.now().plusDays(10).plusDays(durationInDays), createdProject.getFinishDate());
        assertEquals(location, createdProject.getLocation());
    }

    @Test
    public void testProjectServiceCreateProjectForNonExistingLocation() throws InvalidProjectOperationException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Rio Turbio";
        int factor = 1300;
        int closurePercentage = 75;
        String startDate = LocalDate.now().plusDays(10).toString();
        int durationInDays = 60;
        String locationName = "Rio Turbio";
        when(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.ACTIVE.name())).thenReturn(false);
        when(locationRepository.existsById(locationName)).thenReturn(false);
        try {
            projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
        } catch (DataNotFoundException e) {
            String message = "There is no location with name " + locationName;
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceCreateProjectForAlreadyOpenProject() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Rio Turbio";
        int factor = 1300;
        int closurePercentage = 75;
        String startDate = LocalDate.now().plusDays(10).toString();
        int durationInDays = 60;
        String locationName = "Rio Turbio";
        when(locationRepository.existsById(locationName)).thenReturn(true);
        when(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.ACTIVE.name())).thenReturn(true);
        when(projectRepository.existsById(name)).thenReturn(false);
        try {
            projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
        } catch (InvalidProjectOperationException e) {
            String message = "There is already an open project for location " + locationName;
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceCreateProjectForAlreadyCompletedProject() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Santa Clara";
        int factor = 1300;
        int closurePercentage = 75;
        String startDate = LocalDate.now().plusDays(10).toString();
        int durationInDays = 60;
        String locationName = "Conectando Santa Clara";
        when(locationRepository.existsById(locationName)).thenReturn(true);
        when(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.ACTIVE.name())).thenReturn(false);
        when(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.COMPLETE.name())).thenReturn(true);
        when(projectRepository.existsById(name)).thenReturn(false);
        try {
            projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
        } catch (InvalidProjectOperationException e) {
            String message = "There is already a complete project for location " + locationName;
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceCreateProjectForAlreadyUsedProjectName() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Paraiso";
        int factor = 1300;
        int closurePercentage = 75;
        String startDate = LocalDate.now().plusDays(10).toString();
        int durationInDays = 60;
        String locationName = "Paraiso";
        when(locationRepository.existsById(locationName)).thenReturn(true);
        when(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.ACTIVE.name())).thenReturn(false);
        when(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.COMPLETE.name())).thenReturn(false);
        when(projectRepository.existsById(name)).thenReturn(true);
        try {
            projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
        } catch (InvalidProjectOperationException e) {
            String message = "There is already a project with name " + name;
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceCreateProjectForInvalidStartDate() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Santa Clara";
        int factor = 1300;
        int closurePercentage = 75;
        String startDate = LocalDate.now().minusDays(10).toString();
        int durationInDays = 60;
        String locationName = "Conectando Santa Clara";
        when(locationRepository.existsById(locationName)).thenReturn(true);
        when(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.ACTIVE.name())).thenReturn(false);
        when(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.COMPLETE.name())).thenReturn(false);
        when(projectRepository.existsById(name)).thenReturn(false);
        try {
            projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
        } catch (InvalidProjectOperationException e) {
            String message = "Start day of " + startDate + " for project " + name + " is not valid";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceCancelProject() throws DataNotFoundException, InvalidDonationException, InvalidProjectOperationException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Rio Turbio";
        Project project = ProjectBuilder.aProject().withName(name).withStartDate(LocalDate.now()).withDurationInDays(10).build();
        List<Donor> donors = new ArrayList<>();
        Donor donor_1 = DonorBuilder.aDonorUser().withNickname("juan123").withMoney(BigDecimal.valueOf(1000)).build();
        Donor donor_2 = DonorBuilder.aDonorUser().withNickname("maria456").withMoney(BigDecimal.valueOf(1000)).build();
        donor_1.donate(BigDecimal.valueOf(200), "Donation 1", project);
        donor_2.donate(BigDecimal.valueOf(200), "Donation 2", project);
        assertEquals(2, project.getDonations().size());
        assertEquals(2, project.numberOfDonors());
        assertEquals(BigDecimal.valueOf(400), project.totalAmountDonations());
        assertEquals(LocalDate.now().plusDays(10), project.getFinishDate());
        donors.add(donor_1); donors.add(donor_2);
        when(projectRepository.existsById(name)).thenReturn(true);
        when(projectRepository.findById(name)).thenReturn(Optional.of(project));
        when(userRepository.findAll()).thenReturn(donors);
        when(userRepository.save(any())).thenReturn(null);
        when(projectRepository.save(any())).thenReturn(null);
        projectService.cancelProject(name);
        assertEquals(0, project.getDonations().size());
        assertEquals(0, project.numberOfDonors());
        assertEquals(BigDecimal.valueOf(0), project.totalAmountDonations());
        assertEquals(ProjectStatus.CANCELLED.name(), project.getStatus());
    }

    @Test
    public void testProjectServiceCancelProjectForNonExistingProject () throws InvalidProjectOperationException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Rio Turbio";
        when(projectRepository.existsById(name)).thenReturn(false);
        try {
            projectService.cancelProject(name);
        } catch (DataNotFoundException e) {
            String message = "Project " + name + " does not exists";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceCancelProjectForNonActiveProject () throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Rio Turbio";
        String status = ProjectStatus.INCOMPLETE.name();
        Project project = ProjectBuilder.aProject().withName(name).withStatus(status).build();
        when(projectRepository.existsById(name)).thenReturn(true);
        when(projectRepository.findById(name)).thenReturn(Optional.of(project));
        try {
            projectService.cancelProject(name);
        } catch (InvalidProjectOperationException e) {
            String message = "Project " + name + " already has status " + status;
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceGetTopTenDonationStarvedLocations () throws InvalidDonationException {
        MockitoAnnotations.initMocks(this);
        Donor donor = DonorBuilder.aDonorUser().withMoney(BigDecimal.valueOf(1000)).build();
        Location location_1 = LocationBuilder.aLocation().withName("Rio Turbio").build();
        Location location_2 = LocationBuilder.aLocation().withName("Santa Rita").build();
        Project project_1 = ProjectBuilder.aProject().build();
        Project project_2 = ProjectBuilder.aProject().build();
        Project project_3 = ProjectBuilder.aProject().build();
        Project project_4 = ProjectBuilder.aProject().build();
        Project project_5 = ProjectBuilder.aProject().build();
        Project project_6 = ProjectBuilder.aProject().build();
        Project project_7 = ProjectBuilder.aProject().withLocation(location_1).build();
        Project project_8 = ProjectBuilder.aProject().build();
        Project project_9 = ProjectBuilder.aProject().build();
        Project project_10 = ProjectBuilder.aProject().build();
        Project project_11 = ProjectBuilder.aProject().withLocation(location_2).build();
        Project project_12 = ProjectBuilder.aProject().build();
        donor.donate(BigDecimal.valueOf(100), "Donation 1", project_7);
        donor.donate(BigDecimal.valueOf(100), "Donation 2", project_11);
        List<Project> projectList = new ArrayList<>();
        projectList.add(project_1); projectList.add(project_2); projectList.add(project_3);
        projectList.add(project_4); projectList.add(project_5); projectList.add(project_6);
        projectList.add(project_7); projectList.add(project_8); projectList.add(project_9);
        projectList.add(project_10); projectList.add(project_11); projectList.add(project_12);
        when(projectRepository.getProjectsWithStatus(ProjectStatus.ACTIVE.name())).thenReturn(projectList);
        project_7.getDonations().get(0).setDate(LocalDate.now().minusDays(100));
        project_11.getDonations().get(0).setDate(LocalDate.now().plusDays(100));
        List<Location> locations = projectService.getTopTenDonationStarvedLocations();
        assertEquals(10, locations.size());
        assertTrue(locations.contains(location_1));
        assertFalse(locations.contains(location_2));
    }

    @Test
    public void testProjectServiceCloseFinishedProjects () throws InvalidDonationException {
        MockitoAnnotations.initMocks(this);
        Donor donor_1 = DonorBuilder.aDonorUser().withNickname("juan123").withMoney(BigDecimal.valueOf(1000)).build();
        Donor donor_2 = DonorBuilder.aDonorUser().withNickname("maria456").withMoney(BigDecimal.valueOf(1000)).build();
        List<Donor> donorsList = new ArrayList<>();
        donorsList.add(donor_1); donorsList.add(donor_2);
        Project project_1 = ProjectBuilder.aProject().withFactor(100).withClosurePercentage(100).build();
        Project project_2 = ProjectBuilder.aProject().withFactor(1000).withClosurePercentage(100).build();
        Project project_3 = ProjectBuilder.aProject().withFactor(100).withClosurePercentage(100).build();
        Project project_4 = ProjectBuilder.aProject().withFactor(1000).withClosurePercentage(100).build();
        List<Project> projectList = new ArrayList<>();
        projectList.add(project_1); projectList.add(project_2);
        projectList.add(project_3); projectList.add(project_4);

        assertEquals(ProjectStatus.ACTIVE.name(), project_1.getStatus());
        assertEquals(ProjectStatus.ACTIVE.name(), project_2.getStatus());
        assertEquals(ProjectStatus.ACTIVE.name(), project_3.getStatus());
        assertEquals(ProjectStatus.ACTIVE.name(), project_4.getStatus());

        donor_1.donate(BigDecimal.valueOf(99), "Donation 1", project_1);
        donor_2.donate(BigDecimal.valueOf(500), "Donation 2", project_1);
        donor_1.donate(BigDecimal.valueOf(100), "Donation 3", project_2);
        donor_2.donate(BigDecimal.valueOf(100), "Donation 4", project_2);
        donor_1.donate(BigDecimal.valueOf(200), "Donation 5", project_3);
        when(projectRepository.getProjectsWithStatus(ProjectStatus.ACTIVE.name())).thenReturn(projectList);
        when(userRepository.findAll()).thenReturn(donorsList);
        when(donationRepository.findAll()).thenReturn(null);
        doAnswer(InvocationOnMock::callRealMethod).when(donationService).returnDonation(any(), any());
        project_1.setStartDate(LocalDate.now().minusDays(10));
        project_1.setFinishDate(LocalDate.now());
        project_2.setStartDate(LocalDate.now().minusDays(10));
        project_2.setFinishDate(LocalDate.now());
        project_3.setStartDate(LocalDate.now().minusDays(10));
        project_3.setFinishDate(LocalDate.now().plusDays(10));
        project_4.setStartDate(LocalDate.now().minusDays(10));
        project_4.setFinishDate(LocalDate.now().plusDays(10));

        assertEquals(ProjectStatus.COMPLETE.name(), project_1.getStatus());
        assertEquals(2, project_1.getDonations().size());
        assertEquals(ProjectStatus.ACTIVE.name(), project_2.getStatus());
        assertEquals(2, project_2.getDonations().size());
        assertEquals(ProjectStatus.COMPLETE.name(), project_3.getStatus());
        assertEquals(1, project_3.getDonations().size());
        assertEquals(ProjectStatus.ACTIVE.name(), project_4.getStatus());
        assertEquals(0, project_4.getDonations().size());

        projectService.closeFinishedProjects();

        assertEquals(ProjectStatus.COMPLETE.name(), project_1.getStatus());
        assertEquals(2, project_1.getDonations().size());
        assertEquals(ProjectStatus.INCOMPLETE.name(), project_2.getStatus());
        assertEquals(0, project_2.getDonations().size());
        assertEquals(ProjectStatus.COMPLETE.name(), project_3.getStatus());
        assertEquals(1, project_3.getDonations().size());
        assertEquals(ProjectStatus.ACTIVE.name(), project_4.getStatus());
        assertEquals(0, project_4.getDonations().size());

        assertTrue(project_1.hasReachedGoal());
        assertFalse(project_2.hasReachedGoal());
        assertTrue(project_3.hasReachedGoal());
        assertFalse(project_4.hasReachedGoal());
    }

}
