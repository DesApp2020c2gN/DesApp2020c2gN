package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepository repository;

    @Transactional
    public Location save(Location location) {
        return this.repository.save(location);
    }

    public Location findById(String name) throws DataNotFoundException {
        if(repository.existsById(name)){
            return this.repository.findById(name).get();
        }
        else {
            throw new DataNotFoundException("Location " + name + " does not exists");
        }
    }

    public List<Location> findAll() { return this.repository.findAll(); }

}
