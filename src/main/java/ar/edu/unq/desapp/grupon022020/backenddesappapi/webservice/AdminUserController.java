package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.LoginException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.ProjectService;
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

import java.time.LocalDate;


@RestController
@EnableAutoConfiguration
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loginAdmin(@RequestParam("nickname") String nickname,
                                       @RequestParam("password") String password) {
        try {
            userService.loginAdmin(nickname, password);
            return ResponseEntity.ok().body("Admin login successful");
        } catch (LoginException e) {
            return new ResponseEntity<>("Admin login failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> createProject(@RequestParam("name") String name,
                                           @RequestParam("factor") int factor,
                                           @RequestParam("closurePercentage") int closurePercentage,
                                           @RequestParam("startDate") String startDate,
                                           @RequestParam("durationInDays") int durationInDays,
                                           @RequestParam("locationName") String locationName) {
        try {
            Project project = projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
            return new ResponseEntity<>(project, HttpStatus.CREATED);
        } catch (InvalidProjectOperationException | DataNotFoundException e) {
            return new ResponseEntity<>("Project could not be created: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> cancelProject(@RequestParam("name") String name) {
        try {
            projectService.cancelProject(name);
            return ResponseEntity.ok().body("Project " + name + " cancelled");
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Project could not be cancelled: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> closeFinishedProjects() {
        projectService.closeFinishedProjects();
        return ResponseEntity.ok().body("Projects closed for " + LocalDate.now().toString());
    }

}
