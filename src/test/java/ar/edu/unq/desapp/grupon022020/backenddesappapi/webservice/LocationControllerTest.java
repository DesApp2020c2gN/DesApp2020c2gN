package ar.edu.unq.desapp.grupon022020.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    @MockBean
    private LocationService locationService;

    @Autowired
    private LocationController locationController;

    @Test
    public void testLocationControllerAllLocationsStatus() {
        List<Location> locations = new ArrayList<>();
        locations.add(LocationBuilder.aLocation().withName("Santa Clara").build());
        when(locationService.findAll()).thenReturn(locations);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) locationController.allLocations();
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testLocationControllerAllLocationsContent() {
        List<Location> locations = new ArrayList<>();
        Location location_1 = LocationBuilder.aLocation().withName("Santa Clara").build();
        locations.add(location_1);
        when(locationService.findAll()).thenReturn(locations);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) locationController.allLocations();
        assertNotNull(httpResponse.getBody());
        assertEquals(locations, httpResponse.getBody());
    }

    @Test
    public void testLocationControllerGetLocationStatus() throws DataNotFoundException {
        String name = "Santa Clara";
        Location location = LocationBuilder.aLocation().withName(name).build();
        when(locationService.findById(name)).thenReturn(location);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) locationController.getLocation(name);
        assertEquals(HttpStatus.OK, httpResponse.getStatusCode());
    }

    @Test
    public void testLocationControllerGetLocationContent() throws DataNotFoundException {
        String name = "Santa Clara";
        Location location = LocationBuilder.aLocation().withName(name).build();
        when(locationService.findById(name)).thenReturn(location);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) locationController.getLocation(name);
        assertNotNull(httpResponse.getBody());
        assertEquals(location, httpResponse.getBody());
    }

    @Test
    public void testLocationControllerGetLocationException() throws DataNotFoundException {
        String name = "Santa Clara";
        String message = "Location " + name + " does not exists";
        when(locationService.findById(name)).thenThrow(new DataNotFoundException(message));
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) locationController.getLocation(name);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("Location could not be found: " + message, httpResponse.getBody());
    }

    @Test
    public void testLocationControllerCreateLocationStatus() {
        Location location = LocationBuilder.aLocation().withName("Santa Rita").build();
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) locationController.createLocation(location);
        assertEquals(HttpStatus.CREATED, httpResponse.getStatusCode());
    }

    @Test
    public void testLocationControllerCreateLocationContent() {
        Location location = LocationBuilder.aLocation().withName("Santa Rita").build();
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) locationController.createLocation(location);
        assertNotNull(httpResponse.getBody());
        assertEquals(location, httpResponse.getBody());
    }

    @Test
    public void testLocationControllerCreateLocationException() throws DataNotFoundException {
        String name = "Santa Clara";
        String message = "Location " + name + " already exists";
        Location location = LocationBuilder.aLocation().withName(name).build();
        doThrow(new DataNotFoundException(message)).when(locationService).createLocation(location);
        ResponseEntity<String> httpResponse = (ResponseEntity<String>) locationController.createLocation(location);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, httpResponse.getStatusCode());
        assertNotNull(httpResponse.getBody());
        assertEquals("Location could not be created: " + message, httpResponse.getBody());
    }

}
