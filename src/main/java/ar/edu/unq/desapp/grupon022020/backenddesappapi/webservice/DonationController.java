package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.aspects.LogExecutionArguments;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.aspects.LogExecutionTime;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/donations")
@Validated
public class DonationController {

    @Autowired
    private DonationService donationService;

    @LogExecutionTime @LogExecutionArguments
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allDonations() {
        List<Donation> list = donationService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @LogExecutionTime @LogExecutionArguments
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> donate(@RequestParam("nickname") @NotBlank String nickname,
                       @RequestParam("projectName") @NotBlank String projectName,
                       @RequestParam("comment") @NotBlank String comment,
                       @RequestParam("amount") @NotNull @Positive int amount) {
        try {
            Donation donation = donationService.donate(nickname, projectName, comment, amount);
            return ResponseEntity.ok().body(donation);
        } catch (InvalidDonationException | DataNotFoundException e) {
            return new ResponseEntity<>("Donation could not be completed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("One of the arguments is not valid", HttpStatus.BAD_REQUEST);
    }

}
