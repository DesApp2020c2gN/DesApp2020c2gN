package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> allLocations() {
        List<Location> list = locationService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/data/{name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getLocation(@PathVariable("name") String name) {
        try {
            Location location = locationService.findById(name);
            return ResponseEntity.ok().body(location);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Location could not be found: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/data", method = RequestMethod.PUT)
    public ResponseEntity<?> createLocation(@RequestParam("name") String name,
                                            @RequestParam("province") String province,
                                            @RequestParam("population") int population,
                                            @RequestParam("state") String state) {
        try {
            Location location = locationService.createLocation(name, province, population, state);
            return new ResponseEntity<>(location, HttpStatus.CREATED);
        } catch (DataNotFoundException e) {
            return new ResponseEntity<>("Location could not be created: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
