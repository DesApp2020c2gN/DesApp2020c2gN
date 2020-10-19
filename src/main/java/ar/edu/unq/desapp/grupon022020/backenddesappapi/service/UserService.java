package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.LoginException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    //TODO: create test for UserService!

    @Autowired
    private UserRepository repository;
    @Value("${admin.name:NONE}")
    private String adminName;
    @Value("${admin.password:NONE}")
    private String adminPassword;

    @Transactional
    public DonorUser save(DonorUser donorUser) {
        return this.repository.save(donorUser);
    }

    public DonorUser findById(String id) throws DataNotFoundException {
        if(repository.existsById(id)){
            return this.repository.findById(id).get();
        }
        else {
            throw new DataNotFoundException("Donor " + id + " does not exists");
        }
    }

    public List<DonorUser> findAll() {
        return this.repository.findAll();
    }

    public DonorUser createDonorUser(String nickname,
                                     String name,
                                     String mail,
                                     String password,
                                     int money) throws DataNotFoundException {
        if (repository.existsById(nickname)){
            throw new DataNotFoundException("User " + nickname + " already exists");
        }
        //TODO: validate money is zero or a positive number!
        DonorUser donorUser = DonorUserBuilder.aDonorUser().
                withNickname(nickname).
                withName(name).
                withMail(mail).
                withPassword(password).
                withMoney(BigDecimal.valueOf(money)).
                withPoints(0).
                withDonations(new ArrayList<>()).
                build();
        save(donorUser);
        return donorUser;
    }

    public void loginAdmin(String nickname, String password) throws LoginException {
        if(!nickname.equals(adminName)){
            throw new LoginException("Nickname is incorrect");
        }
        if(!password.equals(adminPassword)){
            throw new LoginException("Password is incorrect");
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

}
