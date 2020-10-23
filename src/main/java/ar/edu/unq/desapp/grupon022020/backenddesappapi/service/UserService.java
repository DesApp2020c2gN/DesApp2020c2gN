package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.LoginException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Value("${admin.name:NONE}")
    private String adminName;
    @Value("${admin.password:NONE}")
    private String adminPassword;

    @Transactional
    public Donor save(Donor donor) {
        return this.repository.save(donor);
    }

    public List<Donor> findAll() {
        return this.repository.findAll();
    }

    public Donor findById(String id) throws DataNotFoundException {
        if(repository.existsById(id)){
            return this.repository.findById(id).get();
        }
        else {
            throw new DataNotFoundException("User " + id + " does not exists");
        }
    }

    public void loginDonorUser(String nickname, String password) throws LoginException {
        if(!repository.existsById(nickname)) {
            throw new LoginException("Nickname belongs to a non existing user");
        }
        if(!repository.loginUser(nickname, password)) {
            throw new LoginException("Password is incorrect");
        }
    }

    public void loginAdmin(String nickname, String password) throws LoginException {
        //TODO: create test for this method!
        if(!nickname.equals(adminName)){
            throw new LoginException("Nickname is incorrect");
        }
        if(!password.equals(adminPassword)){
            throw new LoginException("Password is incorrect");
        }
    }

    public void createDonorUser(Donor user) throws DataNotFoundException {
        if (repository.existsById(user.getNickname())){
            throw new DataNotFoundException("User " + user.getNickname() + " already exists");
        }
        save(user);
    }
}
