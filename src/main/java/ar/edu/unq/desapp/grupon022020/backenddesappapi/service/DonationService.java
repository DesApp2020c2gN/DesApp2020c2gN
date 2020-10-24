package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.DonationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.ProjectRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public Donation save(Donation donation) {
        return this.donationRepository.save(donation);
    }

    public List<Donation> findAll() {
        return this.donationRepository.findAll();
    }

    public Donation findById(Integer id) throws DataNotFoundException {
        if(donationRepository.existsById(id)){
            return this.donationRepository.findById(id).get();
        }
        else {
            throw new DataNotFoundException("Donation " + id + " does not exists");
        }
    }

    public Donation donate(String nickname,
                           String projectName,
                           String comment,
                           int amount) throws DataNotFoundException, InvalidDonationException {
        if(!userRepository.existsById(nickname)){
            throw new DataNotFoundException("User " + nickname + " does not exist");
        }
        if(!projectRepository.existsById(projectName)){
            throw new DataNotFoundException("Project " + projectName + " does not exist");
        }
        Donor donor = userRepository.findById(nickname).get();
        Project project = projectRepository.findById(projectName).get();
        Donation donation;
        try {
            donation = donor.donate(BigDecimal.valueOf(amount), comment, project);
            save(donation);
        } catch (InvalidDonationException e) {
            throw new InvalidDonationException(e.getMessage());
        }
        return donation;
    }

    public List<Donation> getTopTenBiggestDonations() {
        List<Donation> donations = donationRepository.findAll();
        List<Donation> sortedDonations =
                donations.stream().
                        sorted(Comparator.comparing(Donation::getAmount).reversed()).
                        limit(10).
                        collect(Collectors.toList());
        return sortedDonations;
    }
}
