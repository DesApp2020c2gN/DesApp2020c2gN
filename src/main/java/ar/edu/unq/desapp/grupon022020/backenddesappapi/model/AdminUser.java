package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;

import java.time.LocalDate;
import java.util.List;

public class AdminUser extends User {

    //TODO: all the logic in this class may be moved into ProjectService!

    public AdminUser(String name, String mail, String password) {
        super(name, mail, password);
    }

    public Project createProject(String name, int factor, int closurePercentage, LocalDate startDate, int durationInDays, Location location) throws InvalidProjectOperationException {
        validateArguments(name, factor, closurePercentage, startDate, durationInDays);
        Project project = ProjectBuilder.aProject().
                withName(name).
                withFactor(factor).
                withClosurePercentage(closurePercentage).
                withStartDate(startDate).
                withDurationInDays(durationInDays).
                withLocation(location).
                build();
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
