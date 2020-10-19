package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allDonations() {
        List<Donation> list = donationService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    public ResponseEntity<?> donate(@RequestParam("nickname") String nickname,
                       @RequestParam("projectName") String projectName,
                       @RequestParam("comment") String comment,
                       @RequestParam("amount") int amount) {
        try {
            Donation donation = donationService.donate(nickname, projectName, comment, amount);
            return ResponseEntity.ok().body(donation);
        } catch (InvalidDonationException | DataNotFoundException e) {
            return new ResponseEntity<>("Donation could not be completed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
