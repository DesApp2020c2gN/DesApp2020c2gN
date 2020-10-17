package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.DonationRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.UserRepository;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    public Donation findById(Integer id) {
        return this.donationRepository.findById(id).get();
    }

    public List<Donation> findAll() {
        return this.donationRepository.findAll();
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
        Donation donation;
        try {
            Optional<DonorUser> donorUser = userRepository.findById(nickname);
            Optional<Project> project = projectRepository.findById(projectName);
            donation = donorUser.get().donate(BigDecimal.valueOf(amount), comment, project.get());
            save(donation);
        } catch (InvalidDonationException e) {
            throw new InvalidDonationException(e.getMessage());
        }
        return donation;
    }
}
