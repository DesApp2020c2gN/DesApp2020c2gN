package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.LoginException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;



import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/users")
@Validated
public class DonorUserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allDonorUsers() {
        List<DonorUser> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/{nickname}", method = RequestMethod.GET)
    public ResponseEntity<?> getDonorUser(@PathVariable("nickname") @NotNull String nickname) {
        try {
            DonorUser donorUser = userService.findById(nickname);
            return ResponseEntity.ok().body(donorUser);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("User could not be found: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestParam("nickname") @NotNull String nickname,
                                       @RequestParam("password") @NotNull String password) {
        try {
            userService.loginDonorUser(nickname, password);
            return ResponseEntity.ok().body("Donor login successful");
        } catch (LoginException e) {
            return new ResponseEntity<>("Donor login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> createDonorUser(@Valid @RequestBody DonorUser user){
        try {
            userService.createDonorUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("User could not be created: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleMethodArgumentNotValidViolationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("At least one of the arguments is not valid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("At least one of the arguments is not valid" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
