package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class System {

    private final List<Project> openProjects;
    private final List<Project> closedProjects;
    private final List<DonorUser> donorUsers;
    private final List<Location> locations;

    public System(List<Project> openProjects, List<Project> closedProjects, List<DonorUser> donorUsers, List<Location> locations) {
        this.openProjects = openProjects;
        this.closedProjects = closedProjects;
        this.donorUsers = donorUsers;
        this.locations = locations;
    }

    public List<Project> getOpenProjects() {
        return openProjects;
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

    public void addNewProject(Project project) {
        this.openProjects.add(project);
    }

    public Optional<Project> getOpenProject(String name) {
        return getOpenProjects().stream().
                filter(project -> project.getName().equals(name)).
                findFirst();
    }

    public void cancelProject(Project projectToCancel) {
        projectToCancel.cancel();
        openProjects.remove(projectToCancel);
        closedProjects.add(projectToCancel);
    }

    public void closeFinishedProjects() {
        List<Project> finishedProjects =
                openProjects.stream().
                        filter(project -> project.getFinishDate().isEqual(LocalDate.now())).
                        collect(Collectors.toList());
        openProjects.removeAll(finishedProjects);
        closedProjects.addAll(finishedProjects);
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
