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
        StringBuilder locationsMessage = new StringBuilder("\n" + "STARVED LOCATIONS RANKING FOR " + LocalDate.now().toString() + "\n");
        for (Location location: locationsRanking)
        {
            locationsMessage.append(" Location: ").append(location.getName()).append("\n");
        }
        List<Donation> donationsRanking = donationService.getTopTenBiggestDonations();
        StringBuilder donationsMessage = new StringBuilder("\n" + "BIGGEST DONATIONS RANKING FOR " + LocalDate.now().toString() + "\n");
        for (Donation donation: donationsRanking)
        {
            donationsMessage.append(" Donation: ").append(donation.getId());
            donationsMessage.append(" Amount: ").append(donation.getAmount());
            donationsMessage.append(" Donor: ").append(donation.getDonorNickname());
            donationsMessage.append(" Project: ").append(donation.getProjectName()).append("\n");
        }
        write(locationsMessage.toString() + donationsMessage.toString());
    }

    public void write(String message) {
        String fileName = "./src/main/resources/rankings.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
            writer.append(' ' + message);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
