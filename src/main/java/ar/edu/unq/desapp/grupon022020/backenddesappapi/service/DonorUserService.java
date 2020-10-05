package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.DonorUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DonorUserService {

    @Autowired
    private DonorUserRepository repository;

    @Transactional
    public DonorUser save(DonorUser donorUser) {
        return this.repository.save(donorUser);
    }

    public DonorUser findByID(Integer id) {
        return this.repository.findById(id).get();
    }

    public List<DonorUser> findAll() {
        return this.repository.findAll();
    }
}
