package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@EnableAutoConfiguration
@RequestMapping("/scheduler")
public class SchedulerController {

    @Autowired
    private SchedulerService schedulerService;

    @RequestMapping(value = "/end", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> closeFinishedProjects() {
        schedulerService.closeFinishedProjects();
        return ResponseEntity.ok().body("Projects closed for " + LocalDate.now().toString());
    }

    @RequestMapping(value = "/ranking", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getRankings() {
        schedulerService.generateRankings();
        return ResponseEntity.ok().body("Rankings generated");
    }
}
