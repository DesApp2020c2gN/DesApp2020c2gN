package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.AdminUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.AdminUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.DonationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.LocationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.ProjectRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.UserRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.utils.CommonTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    //TODO: create test for ProjectService!

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DonationRepository donationRepository;

    @Transactional
    public Project save(Project project) {
        return this.projectRepository.save(project);
    }

    public Project findById(String name) throws DataNotFoundException {
        if(projectRepository.existsById(name)){
            return this.projectRepository.findById(name).get();
        }
        else {
            throw new DataNotFoundException("Project " + name + " does not exists");
        }
    }

    public List<Project> findAll() {
        return this.projectRepository.findAll();
    }

    public Project createProject(String name,
                                 int factor,
                                 int closurePercentage,
                                 String startDate,
                                 int durationInDays,
                                 String locationName) throws InvalidProjectOperationException, DataNotFoundException {
        if(projectRepository.existsOpenProject(locationName, LocalDate.now())){
            throw new InvalidProjectOperationException("There is already an open project for location " + locationName);
        }
        if(!locationRepository.existsById(locationName)){
            throw new DataNotFoundException("There is no location with name " + locationName);
        }
        Location location = locationRepository.findById(locationName).get();
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        Project project = adminUser.createProject(name, factor, closurePercentage, LocalDate.parse(startDate), durationInDays, location);
        save(project);
        return project;
    }

    public void cancelProject(String name) throws DataNotFoundException {
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        if(!projectRepository.existsById(name)){
            throw new DataNotFoundException("Project " + name + " does not exists");
        }
        Project projectToCancel = findById(name);
        List<Donation> donationsToReturn = projectToCancel.getDonations();
        List<DonorUser> donorsList = donorsList(projectToCancel);
        for (Donation donation: donationsToReturn)
        {
            donationRepository.deleteDonation(donation.getId());
        }
        adminUser.cancelProject(projectToCancel, donorsList);
        for (DonorUser user: donorsList)
        {
           userRepository.save(user);
        }
        projectRepository.save(projectToCancel);
    }

    public void closeFinishedProjects(){
        //TODO: review this method!
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        List<Project> projects = projectRepository.findAll();
        List<Project> finishedProjects = projects.stream().
                filter(project -> project.getFinishDate().isEqual(LocalDate.now())).
                collect(Collectors.toList());
        List<Project> incompleteProjects = finishedProjects.stream().
                filter(project -> !project.hasReachedGoal()).
                collect(Collectors.toList());
        for (Project project: incompleteProjects)
        {
            List<Donation> donationsToReturn = project.getDonations();
            for (Donation donation: donationsToReturn)
            {
                donationRepository.deleteDonation(donation.getId());
            }
            List<DonorUser> donorsList = donorsList(project);
            adminUser.returnDonations(project, donorsList);
            for (DonorUser user: donorsList)
            {
                userRepository.save(user);
            }
            projectRepository.save(project);
        }
    }

    public List<Location> getTopTenDonationStarvedLocations() {
        //TODO: review this method!
        List<Project> projects = projectRepository.findAll();
        List<Location> sortedLocations =
                projects.stream()
                        .sorted(Comparator.comparing(this::getLastDonationDate))
                        .map(Project::getLocation)
                        .collect(Collectors.toList());
        return CommonTools.getFirstTenIfExists(sortedLocations);
    }

    private List<DonorUser> donorsList(Project project){
        List<String> donorsNicknames = project.donors();
        List<DonorUser> donorsList = userRepository.findAll().stream().
                filter(donorUser -> donorsNicknames.contains(donorUser.getNickname())).
                collect(Collectors.toList());
        return donorsList;
    }

    private LocalDate getLastDonationDate(Project project) {
        return project
                .getLastDonation()
                .map(Donation::getDate)
                .orElse(project.getStartDate());
    }

}
