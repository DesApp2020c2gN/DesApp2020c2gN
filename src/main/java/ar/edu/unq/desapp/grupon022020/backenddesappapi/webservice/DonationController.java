package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.DonationService;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.DonorUserService;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private DonorUserService donorUserService;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allDonations() {
        List<Donation> list = donationService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    public void donate(@RequestParam("nickname") String nickname,
                       @RequestParam("projectName") String projectName,
                       @RequestParam("comment") String comment,
                       @RequestParam("amount") int amount) throws DataNotFoundException {
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
