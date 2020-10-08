package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.DonationService;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.DonorUserService;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.LocationService;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@EnableAutoConfiguration
public class TestController {

    @Autowired
    private LocationService locationService;
    @Autowired
    private DonationService donationService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DonorUserService donorUserService;

    @RequestMapping(value = "/api/locations", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allLocations() {
        List<Location> list = locationService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/api/locations/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getLocation(@PathVariable("name") String name) {
        Location location = locationService.findByID(name);
        return ResponseEntity.ok().body(location);
    }

    @RequestMapping(value = "/api/projects", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allProjects() {
        List<Project> list = projectService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/api/projects/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable("name") String name){
        Project project = projectService.findByID(name);
        return ResponseEntity.ok().body(project);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allDonorUsers() {
        List<DonorUser> list = donorUserService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/api/users/{nickname}", method = RequestMethod.GET)
    public ResponseEntity<?> getDonorUser(@PathVariable("nickname") String nickname){
        DonorUser donorUser = donorUserService.findByID(nickname);
        return ResponseEntity.ok().body(donorUser);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
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


    @RequestMapping(value = "/api/donations", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allDonations() {
        List<Donation> list = donationService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/api/donations", method = RequestMethod.PUT)
    public void donate(@RequestParam("nickname") String nickname,
                                @RequestParam("projectName") String projectName,
                                @RequestParam("comment") String comment,
                                @RequestParam("amount") int amount){
        DonorUser donorUser = donorUserService.findByID(nickname);
        Project project = projectService.findByID(projectName);
        Donation donation = null;
        try {
            donation = donorUser.donate(BigDecimal.valueOf(amount), comment, project);
        } catch (InvalidDonationException e) {
            e.printStackTrace();
        }
        donationService.save(donation);
    }

}
