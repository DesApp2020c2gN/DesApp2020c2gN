package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class System {

    private final List<Project> projects;
    private final List<DonorUser> donorUsers;
    private final List<Location> locations;

    public System(List<Project> projects, List<DonorUser> donorUsers, List<Location> locations) {
        this.projects = projects;
        this.donorUsers = donorUsers;
        this.locations = locations;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public List<DonorUser> getUsers() {
        return donorUsers;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addNewProject(Project project) {
        this.projects.add(project);
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
