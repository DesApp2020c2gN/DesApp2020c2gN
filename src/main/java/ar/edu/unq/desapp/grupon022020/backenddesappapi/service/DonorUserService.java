package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.DonorUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DonorUserService {

    @Autowired
    private DonorUserRepository repository;

    @Transactional
    public DonorUser save(DonorUser donorUser) {
        return this.repository.save(donorUser);
    }

    public DonorUser findByID(String id) throws DataNotFoundException {
        try {
            return this.repository.findById(id).get();
        }
        catch (NoSuchElementException e){
            throw  new DataNotFoundException("User " + id + " is not a valid user");
        }
    }

    public List<DonorUser> findAll() {
        return this.repository.findAll();
    }

}
