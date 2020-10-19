package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;

import java.time.LocalDate;
import java.util.List;

public class AdminUser extends User {

    private final Manager manager;

    public AdminUser(String name, String mail, String password, Manager manager) {
        super(name, mail, password);
        this.manager = manager;
    }

    public List<Location> getLocations() {
        return manager.getLocations();
    }

    public List<Project> getOpenProjects() {
        return manager.getOpenProjects();
    }

    public List<DonorUser> getUsers() {
        return manager.getUsers();
    }

    public Project createProject(String name, int factor, int closurePercentage, LocalDate startDate, int durationInDays, Location location) throws InvalidProjectOperationException {
        //TODO: validate factor and durationInDays are positive numbers and closurePercentage is between 1 and 100!
        if (startDate.isBefore(LocalDate.now())) {
            throw new InvalidProjectOperationException("Start day of " + startDate.toString() + " for project " + name + " is not valid");
        }
        Project project = ProjectBuilder.aProject().
                withName(name).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                withStartDate(startDate).
                withDurationInDays(durationInDays).
                withLocation(location).
                build();
        this.manager.addNewProject(project);
        return project;
    }

    public void cancelProject(Project project, List<DonorUser> donorsList) {
        project.cancel();
        returnDonations(project, donorsList);
    }

    public void returnDonations(Project projectToCancel, List<DonorUser> donorsList) {
        List<Donation> donationsToReturn = projectToCancel.getDonations();
        donationsToReturn.forEach(donation -> getUser(donation.getDonorNickname(), donorsList).undoDonation(donation));
        projectToCancel.undoDonations();
    }

    private DonorUser getUser(String donorNickname, List<DonorUser> donorsList) {
        return donorsList.stream().filter(donorUser -> donorUser.getNickname().equals(donorNickname)).findFirst().get();
    }

}
