package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
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
        //TODO: add validation in case a project for this location was completed!
        Location location = locationRepository.findById(locationName).get();
        validateArguments(name, factor, closurePercentage, LocalDate.parse(startDate), durationInDays);
        Project project = ProjectBuilder.aProject().
                withName(name).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                withStartDate(LocalDate.parse(startDate)).
                withDurationInDays(durationInDays).
                withLocation(location).
                build();
        save(project);
        return project;
    }

    private void validateArguments(String name, int factor, int closurePercentage, LocalDate startDate, int durationInDays) throws InvalidProjectOperationException {
        if (startDate.isBefore(LocalDate.now())) {
            throw new InvalidProjectOperationException("Start day of " + startDate.toString() + " for project " + name + " is not valid");
        }
        if (!(factor > 0)) {
            throw new InvalidProjectOperationException("Project " + name + " must have a positive factor");
        }
        if (!(durationInDays > 0)) {
            throw new InvalidProjectOperationException("Project " + name + " must have a positive duration");
        }
        if (!(closurePercentage > 0 && closurePercentage <= 100)) {
            throw new InvalidProjectOperationException("Project " + name + " must have a percentage between 1 and 100");
        }
    }

    public void cancelProject(String name) throws DataNotFoundException {
        if(!projectRepository.existsById(name)){
            throw new DataNotFoundException("Project " + name + " does not exists");
        }
        //TODO: add validation in case the project was completed!
        Project projectToCancel = findById(name);
        List<Donation> donationsToReturn = projectToCancel.getDonations();
        List<Donor> donorsList = donorsList(projectToCancel);
        for (Donation donation: donationsToReturn)
        {
            donationRepository.deleteDonation(donation.getId());
        }
        cancelProject(projectToCancel, donorsList);
        for (Donor user: donorsList)
        {
           userRepository.save(user);
        }
        projectRepository.save(projectToCancel);
    }

    public void cancelProject(Project project, List<Donor> donorsList) {
        project.cancel();
        returnDonations(project, donorsList);
    }

    public void returnDonations(Project projectToCancel, List<Donor> donorsList) {
        List<Donation> donationsToReturn = projectToCancel.getDonations();
        donationsToReturn.forEach(donation -> getUser(donation.getDonorNickname(), donorsList).undoDonation(donation));
        projectToCancel.undoDonations();
    }

    private Donor getUser(String donorNickname, List<Donor> donorsList) {
        return donorsList.stream().filter(donorUser -> donorUser.getNickname().equals(donorNickname)).findFirst().get();
    }

    public void closeFinishedProjects(){
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
            List<Donor> donorsList = donorsList(project);
            returnDonations(project, donorsList);
            for (Donor user: donorsList)
            {
                userRepository.save(user);
            }
            projectRepository.save(project);
        }
    }

    public List<Location> getTopTenDonationStarvedLocations() {
        List<Project> projects = projectRepository.findAll();
        List<Location> sortedLocations =
                projects.stream()
                        .sorted(Comparator.comparing(this::getLastDonationDate))
                        .map(Project::getLocation)
                        .collect(Collectors.toList());
        return CommonTools.getFirstTenIfExists(sortedLocations);
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

}
