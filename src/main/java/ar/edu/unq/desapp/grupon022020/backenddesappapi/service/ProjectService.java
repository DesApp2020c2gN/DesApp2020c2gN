package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.ProjectStatus;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.DonationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.LocationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.ProjectRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private DonationService donationService;

    @Transactional
    public Project save(Project project) {
        return this.projectRepository.save(project);
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public Project findById(String name) throws DataNotFoundException {
        if(projectRepository.existsById(name)){
            return this.projectRepository.findById(name).get();
        }
        else {
            throw new DataNotFoundException("Project " + name + " does not exists");
        }
    }

    public Project createProject(String name, int factor, int closurePercentage, String startDate, int durationInDays, String locationName) throws InvalidProjectOperationException, DataNotFoundException {
        validateProjectCreation(name, LocalDate.parse(startDate), locationName);
        Location location = locationRepository.findById(locationName).get();
        Project project = ProjectBuilder.aProject().
                withName(name).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                withStartDate(LocalDate.parse(startDate)).
                withDurationInDays(durationInDays).
                withLocation(location).
                withStatus(ProjectStatus.ACTIVE.name()).
                build();
        save(project);
        return project;
    }

    private void validateProjectCreation(String name, LocalDate startDate, String locationName) throws InvalidProjectOperationException, DataNotFoundException {
        if(!locationRepository.existsById(locationName)){
            throw new DataNotFoundException("There is no location with name " + locationName);
        }
        if(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.ACTIVE.name())){
            throw new InvalidProjectOperationException("There is already an open project for location " + locationName);
        }
        if(projectRepository.existsProjectForLocationWithStatus(locationName, ProjectStatus.COMPLETE.name())){
            throw new InvalidProjectOperationException("There is already a complete project for location " + locationName);
        }
        if(projectRepository.existsById(name)){
            throw new InvalidProjectOperationException("There is already a project with name " + name);
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new InvalidProjectOperationException("Start day of " + startDate.toString() + " for project " + name + " is not valid");
        }
    }

    public void cancelProject(String name) throws DataNotFoundException, InvalidProjectOperationException {
        Project projectToCancel = findById(name);
        validateProjectCancellation(projectToCancel);
        List<Donation> donationsToReturn = projectToCancel.getDonations();
        List<Donor> donorsList = donorsList(projectToCancel);
        revertDonations(donationsToReturn, projectToCancel, donorsList);
    }

    private void validateProjectCancellation(Project project) throws DataNotFoundException, InvalidProjectOperationException {
        if(!projectRepository.existsById(project.getName())){
            throw new DataNotFoundException("Project " + project.getName() + " does not exists");
        }
        if(!project.getStatus().equals(ProjectStatus.ACTIVE.name())){
            throw new InvalidProjectOperationException("Project " + project.getName() + " already has status " + project.getStatus());
        }
    }

    private void revertDonations(List<Donation> donationsToReturn, Project projectToCancel, List<Donor> donorsList) {
        for (Donation donation: donationsToReturn)
        {
            donationRepository.deleteDonation(donation.getId());
        }
        projectToCancel.cancel();
        returnDonations(projectToCancel, donorsList);
        for (Donor user: donorsList)
        {
            userRepository.save(user);
        }
        projectRepository.save(projectToCancel);
    }

    private void returnDonations(Project projectToCancel, List<Donor> donorsList) {
        List<Donation> donationsToReturn = projectToCancel.getDonations();
        donationsToReturn.forEach(donation -> donationService.returnDonation(donation, getUser(donation.getDonorNickname(), donorsList)));
        projectToCancel.undoDonations();
    }

    private Donor getUser(String donorNickname, List<Donor> donorsList) {
        return donorsList.stream().filter(donorUser -> donorUser.getNickname().equals(donorNickname)).findFirst().get();
    }

    public void closeFinishedProjects() {
        List<Project> activeProjects = projectRepository.getProjectsWithStatus(ProjectStatus.ACTIVE.name());
        List<Project> finishingProjects = activeProjects.stream().
                filter(project -> project.getFinishDate().isEqual(LocalDate.now()) && !project.hasReachedGoal()).
                collect(Collectors.toList());
        for (Project project: finishingProjects)
        {
            List<Donation> donationsToReturn = project.getDonations();
            for (Donation donation: donationsToReturn)
            {
                donationRepository.deleteDonation(donation.getId());
            }
            List<Donor> donorsList = donorsList(project);
            returnDonations(project, donorsList);
            project.setStatus(ProjectStatus.INCOMPLETE.name());
            for (Donor user: donorsList)
            {
                userRepository.save(user);
            }
            projectRepository.save(project);
        }
    }

    public List<Location> getTopTenDonationStarvedLocations() {
        List<Project> projects = projectRepository.getProjectsWithStatus(ProjectStatus.ACTIVE.name());
        List<Location> sortedLocations =
                projects.stream().
                        sorted(Comparator.comparing(this::getLastDonationDate)).
                        map(Project::getLocation).
                        limit(10).
                        collect(Collectors.toList());
        return sortedLocations;
    }

    private List<Donor> donorsList(Project project){
        List<String> donorsNicknames = project.donors();
        List<Donor> donorsList = userRepository.findAll().stream().
                filter(donorUser -> donorsNicknames.contains(donorUser.getNickname())).
                collect(Collectors.toList());
        return donorsList;
    }

    private LocalDate getLastDonationDate(Project project) {
        return project
                .lastDonation()
                .map(Donation::getDate)
                .orElse(project.getStartDate());
    }

    public List<Project> findAllEndingThisMonth() {
        List<Project> allProjects = this.findAll();
        List<Project> filteredProjects = allProjects.stream().
                filter(project -> project.getStatus() == ProjectStatus.ACTIVE.name() &&
                        project.getFinishDate().getYear() == LocalDate.now().getYear() &&
                        project.getFinishDate().getMonth() == LocalDate.now().getMonth()).
                collect(Collectors.toList());
        return filteredProjects;
    }
}
