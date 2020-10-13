package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allProjects() {
        List<Project> list = projectService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/data/{name}", method = RequestMethod.GET)
    public ResponseEntity<?> getProject(@PathVariable("name") String name){
        // TODO: Check if a non existing project is requested!
        Project project = projectService.findById(name);
        return ResponseEntity.ok().body(project);
    }
}
