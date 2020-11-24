package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.LoginException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/users")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allDonorUsers() {
        List<Donor> list = userService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/{nickname}", method = RequestMethod.GET)
    public ResponseEntity<?> getDonorUser(@PathVariable("nickname") @NotBlank String nickname) {
        try {
            Donor donor = userService.findById(nickname);
            return ResponseEntity.ok().body(donor);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("User could not be found: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/donor", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestParam("nickname") @NotBlank String nickname,
                                       @RequestParam("password") @NotBlank String password) {
        try {
            userService.loginDonorUser(nickname, password);
            return ResponseEntity.ok().body("Donor login successful");
        } catch (LoginException e) {
            return new ResponseEntity<>("Donor login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loginAdmin(@RequestParam("nickname") @NotBlank String nickname,
                                        @RequestParam("password") @NotBlank String password) {
        try {
            userService.loginAdmin(nickname, password);
            return ResponseEntity.ok().body("Admin login successful");
        } catch (LoginException e) {
            return new ResponseEntity<>("Admin login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<?> createDonorUser(@Valid @RequestBody Donor user){
        try {
            userService.createDonorUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("User could not be created: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/loginbymail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loginByMail(@RequestParam("mail") @NotBlank String mail) {
        try {
            return ResponseEntity.ok().body(userService.loginByMail(mail));
        } catch (LoginException e) {
            return new ResponseEntity<>("User login failed: " + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("User login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleMethodArgumentNotValidViolationException(MethodArgumentNotValidException e) {
        return new ResponseEntity<>("The argument is not valid", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("One of the arguments is not valid", HttpStatus.BAD_REQUEST);
    }
}
