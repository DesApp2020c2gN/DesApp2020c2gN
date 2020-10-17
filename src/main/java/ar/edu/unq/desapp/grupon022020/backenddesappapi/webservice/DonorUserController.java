package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.LoginException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.UserService;
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

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/users")
public class DonorUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestParam("nickname") String nickname,
                                       @RequestParam("password") String password) {
        try {
            userService.loginDonorUser(nickname, password);
        } catch (LoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body("Login successful");
    }

    @RequestMapping(value = "/data/{nickname}", method = RequestMethod.GET)
    public ResponseEntity<?> getDonorUser(@PathVariable("nickname") String nickname) {
        DonorUser donorUser;
        try {
            donorUser = userService.findById(nickname);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body(donorUser);
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allDonorUsers() {
        List<DonorUser> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    public ResponseEntity<?> createDonorUser(@RequestParam("nickname") String nickname,
                                @RequestParam("name") String name,
                                @RequestParam("mail") String mail,
                                @RequestParam("password") String password,
                                @RequestParam("money") int money){
        DonorUser donorUser;
        try {
            userService.findById(nickname);
        } catch (DataNotFoundException e) {
            donorUser = userService.createDonorUser(nickname, name, mail, password, money);
            return new ResponseEntity<>(donorUser, HttpStatus.CREATED);
        }
        return new ResponseEntity<>("User " + nickname + " already exists", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
