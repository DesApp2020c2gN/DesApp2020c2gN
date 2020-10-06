package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DonationService {

    @Autowired
    private DonationRepository repository;

    @Transactional
    public Donation save(Donation donation) {
        return this.repository.save(donation);
    }

    public Donation findByID(Integer id) {
        return this.repository.findById(id).get();
    }

    public List<Donation> findAll() {
        return this.repository.findAll();
    }
}
