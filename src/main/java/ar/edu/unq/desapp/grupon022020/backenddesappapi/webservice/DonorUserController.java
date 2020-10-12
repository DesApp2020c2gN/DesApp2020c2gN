package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.DonorUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/users")
public class DonorUserController {

    @Autowired
    private DonorUserService donorUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestParam("nickname") String nickname,
                                       @RequestParam("password") String password) {
        DonorUser donorUser;
        try {
            donorUser = donorUserService.findByID(nickname);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(donorUser != null && donorUser.getPassword().equals(password)){
            return ResponseEntity.ok().body(donorUser);
        }
        return new ResponseEntity<>("Password is incorrect", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/data/{nickname}", method = RequestMethod.GET)
    public ResponseEntity<?> getDonorUser(@PathVariable("nickname") String nickname) {
        DonorUser donorUser;
        try {
            donorUser = donorUserService.findByID(nickname);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(donorUser);
    }
    
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allDonorUsers() {
        List<DonorUser> list = donorUserService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    public void insertDonorUser(@RequestParam("nickname") String nickname,
                                @RequestParam("name") String name,
                                @RequestParam("mail") String mail,
                                @RequestParam("password") String password,
                                @RequestParam("money") int money){
        DonorUser donorUser = DonorUserBuilder.aDonorUser().
                withNickname(nickname).
                withName(name).
                withMail(mail).
                withPassword(password).
                withMoney(BigDecimal.valueOf(money)).
                withPoints(0).
                withDonations(new ArrayList<>()).
                build();
        donorUserService.save(donorUser);
    }
}
