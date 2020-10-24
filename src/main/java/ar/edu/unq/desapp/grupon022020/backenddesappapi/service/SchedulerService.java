package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private DonationService donationService;

    @Scheduled(cron = "0 55 23 ? * * ")
    public void closeFinishedProjects(){
        projectService.closeFinishedProjects();
    }

    @Scheduled(cron = "0 59 23 ? * 7 ")
    public void generateRankings(){
        List<Location> locationsRanking = projectService.getTopTenDonationStarvedLocations();
        write("\n" + "STARVED LOCATIONS RANKING FOR " + LocalDate.now().toString() + "\n");
        for (Location location: locationsRanking)
        {
            write("Location: " + location.getName() + "\n");
        }
        List<Donation> donationsRanking = donationService.getTopTenBiggestDonations();
        write("\n" + "BIGGEST DONATIONS RANKING FOR " + LocalDate.now().toString() + "\n");
        for (Donation donation: donationsRanking)
        {
            write("Donation: " + donation.getId());
            write("Amount: " + donation.getAmount());
            write("Donor: " + donation.getDonorNickname());
            write("Project: " + donation.getProjectName() + "\n");
        }
    }

    public void write(String message) {
        String fileName = "./src/main/resources/rankings.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
            writer.append(' ');
            writer.append(message);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
