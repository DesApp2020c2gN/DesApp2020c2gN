package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperation;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Manager {

    private final List<Project> openProjects;
    private final List<Project> closedProjects;
    private final List<DonorUser> donorUsers;
    private final List<Location> locations;

    public Manager(List<Project> openProjects, List<Project> closedProjects, List<DonorUser> donorUsers, List<Location> locations) {
        this.openProjects = openProjects;
        this.closedProjects = closedProjects;
        this.donorUsers = donorUsers;
        this.locations = locations;
    }

    public List<Project> getOpenProjects() {
        return openProjects;
    }

    public List<Project> getOpenProjectsEndingThisMonth() {
        return openProjects.stream().
                filter(project -> project.getFinishDate().getMonth().equals(LocalDate.now().getMonth())).
                collect(Collectors.toList());
    }

    public List<Project> getClosedProjects() {
        return closedProjects;
    }

    public List<DonorUser> getUsers() {
        return donorUsers;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addNewProject(Project newProject) throws InvalidProjectOperation {
        boolean isCurrentlyOpen = this.openProjects.stream().
                anyMatch(project -> project.getLocation().equals(newProject.getLocation()));
        boolean isAlreadyCompleted = this.closedProjects.stream().
                anyMatch(project -> project.getLocation().equals(newProject.getLocation()) && project.hasReachedGoal());
        if(isCurrentlyOpen){
            throw new InvalidProjectOperation("A project for location " + newProject.getLocation().getName() + " is currently open");
        }
        if(isAlreadyCompleted){
            throw new InvalidProjectOperation("A project for location " + newProject.getLocation().getName() + " is already completed");
        }
        this.openProjects.add(newProject);
    }

    public Optional<Project> getOpenProject(String name) {
        return getOpenProjects().stream().
                filter(project -> project.getName().equals(name)).
                findFirst();
    }

    private Optional<DonorUser> getUser(String donorNickname) {
        return getUsers().stream().
                filter(donorUser -> donorUser.getNickname().equals(donorNickname)).
                findFirst();
    }

    public void cancelProject(Project projectToCancel) {
        projectToCancel.cancel();
        setAsClosed(projectToCancel);
        returnDonations(projectToCancel);
    }

    public void closeFinishedProjects() {
        List<Project> finishedProjects =
                openProjects.stream().
                        filter(project -> project.getFinishDate().isEqual(LocalDate.now())).
                        collect(Collectors.toList());
        finishedProjects.forEach(this::setAsClosed);
        finishedProjects.stream().
                filter(project -> !project.hasReachedGoal()).
                forEach(project -> returnDonations(project));
    }

    private void setAsClosed(Project project) {
        openProjects.remove(project);
        closedProjects.add(project);
    }


    private void returnDonations(Project projectToCancel) {
        List<Donation> donationsToReturn = projectToCancel.getDonations();
        donationsToReturn.stream().
                forEach(donation -> getUser(donation.getDonorNickname()).
                        ifPresent(donorUser -> donorUser.undoDonation(donation)));
        projectToCancel.undoDonations();
    }

    public List<Donation> getTopTenBiggestDonations() {
        List<Donation> topTenList =
                donorUsers.stream().
                        map(user -> user.getDonations()).
                        flatMap(donations -> donations.stream()).
                        sorted(Comparator.comparing(Donation::getAmount).reversed()).
                        collect(Collectors.toList()).subList(0, 10);
        return topTenList;
    }

}
