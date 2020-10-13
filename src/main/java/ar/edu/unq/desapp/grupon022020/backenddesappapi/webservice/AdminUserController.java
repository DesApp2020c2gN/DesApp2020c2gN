package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.AdminUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.AdminUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidProjectOperationException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.LocationService;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private ProjectService projectService;
    @Autowired
    private LocationService locationService;
    @Value("${admin.name:NONE}")
    private String adminName;
    @Value("${admin.password:NONE}")
    private String adminPassword;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> loginAdmin(@RequestParam("nickname") String nickname,
                                       @RequestParam("password") String password) {
        if(!nickname.equals(adminName)){
            return new ResponseEntity<>("Nickname is incorrect", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(!password.equals(adminPassword)){
            return new ResponseEntity<>("Password is incorrect", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok().body("Login successful");
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> createProject(@RequestParam("name") String name,
                                           @RequestParam("factor") int factor,
                                           @RequestParam("closurePercentage") int closurePercentage,
                                           @RequestParam("startDate") String startDate,
                                           @RequestParam("durationInDays") int durationInDays,
                                           @RequestParam("locationName") String locationName) {
        Project project;
        Location location = locationService.findByID(locationName);
        AdminUser adminUser = AdminUserBuilder.anAdminUser().build();
        try {
            project = adminUser.createProject(name, factor, closurePercentage, LocalDate.parse(startDate), durationInDays, location);
        } catch (InvalidProjectOperationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        projectService.save(project);
        return ResponseEntity.ok().body(project);
    }

}
