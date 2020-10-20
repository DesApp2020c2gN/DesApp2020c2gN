package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
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
        when(projectRepository.existsOpenProject(locationName, LocalDate.now())).thenReturn(false);
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
    public void testProjectServiceCreateProjectForAlreadyOpenProject() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Rio Turbio";
        int factor = 1300;
        int closurePercentage = 75;
        String startDate = LocalDate.now().plusDays(10).toString();
        int durationInDays = 60;
        String locationName = "Rio Turbio";
        when(projectRepository.existsOpenProject(locationName, LocalDate.now())).thenReturn(true);
        try {
            projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
        } catch (InvalidProjectOperationException e) {
            String message = "There is already an open project for location " + locationName;
            assertEquals(message, e.getMessage());
        }
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
        when(projectRepository.existsOpenProject(locationName, LocalDate.now())).thenReturn(false);
        when(locationRepository.existsById(locationName)).thenReturn(false);
        try {
            projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
        } catch (DataNotFoundException e) {
            String message = "There is no location with name " + locationName;
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testProjectServiceCancelProject() throws DataNotFoundException, InvalidDonationException {
        MockitoAnnotations.initMocks(this);
        String name = "Conectando Rio Turbio";
        Project project = ProjectBuilder.aProject().withName(name).withStartDate(LocalDate.now()).withDurationInDays(10).build();
        List<DonorUser> donors = new ArrayList<>();
        DonorUser donor_1 = DonorUserBuilder.aDonorUser().withNickname("juan123").withMoney(BigDecimal.valueOf(1000)).build();
        DonorUser donor_2 = DonorUserBuilder.aDonorUser().withNickname("maria456").withMoney(BigDecimal.valueOf(1000)).build();
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
        assertEquals(LocalDate.now().minusDays(1), project.getFinishDate());
    }

    @Test
    public void testProjectServiceCancelProjectForNonExistingProject () {
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
    public void testProjectServiceGetTopTenDonationStarvedLocations () throws InvalidDonationException {
        MockitoAnnotations.initMocks(this);
        DonorUser donor = DonorUserBuilder.aDonorUser().withMoney(BigDecimal.valueOf(1000)).build();
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
        when(projectRepository.findAll()).thenReturn(projectList);
        project_7.getDonations().get(0).setDate(LocalDate.now().minusDays(100));
        project_11.getDonations().get(0).setDate(LocalDate.now().plusDays(100));
        List<Location> locations = projectService.getTopTenDonationStarvedLocations();
        assertEquals(10, locations.size());
        assertTrue(locations.contains(location_1));
        assertFalse(locations.contains(location_2));
    }

}
