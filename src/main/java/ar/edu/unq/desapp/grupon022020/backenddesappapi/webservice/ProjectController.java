package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/projects")
@Validated
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allProjects() {
        List<Project> list = projectService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable("name") @NotBlank String name) {
        try {
            Project project = projectService.findById(name);
            return ResponseEntity.ok().body(project);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Project could not be found: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> createProject(@RequestParam("name") @NotBlank String name,
                                           @RequestParam("factor") @Positive int factor,
                                           @RequestParam("closurePercentage") @Min(value=1) @Max(value=100) int closurePercentage,
                                           @RequestParam("startDate") @Future String startDate,
                                           @RequestParam("durationInDays") @Positive int durationInDays,
                                           @RequestParam("locationName") @NotBlank String locationName) {
        try {
            Project project = projectService.createProject(name, factor, closurePercentage, startDate, durationInDays, locationName);
            return new ResponseEntity<>(project, HttpStatus.CREATED);
        } catch (InvalidProjectOperationException | DataNotFoundException e) {
            return new ResponseEntity<>("Project could not be created: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> cancelProject(@RequestParam("name") @NotBlank String name) {
        try {
            projectService.cancelProject(name);
            return ResponseEntity.ok().body("Project " + name + " cancelled");
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Project could not be cancelled: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/end", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> closeFinishedProjects() {
        projectService.closeFinishedProjects();
        return ResponseEntity.ok().body("Projects closed for " + LocalDate.now().toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("One of the arguments is not valid" + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
