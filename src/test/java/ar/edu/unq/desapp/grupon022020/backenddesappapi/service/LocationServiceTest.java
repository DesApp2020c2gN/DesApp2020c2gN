package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.DataNotFoundException;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class LocationServiceTest {

    @InjectMocks
    private LocationService locationService;

    @Mock
    private LocationRepository locationRepository;

    @Test
    public void testLocationServiceFindAll() {
        MockitoAnnotations.initMocks(this);
        List<Location> locations = new ArrayList<>();
        locations.add(LocationBuilder.aLocation().withName("Santa Rita").build());
        locations.add(LocationBuilder.aLocation().withName("Colon").build());
        when(locationRepository.findAll()).thenReturn(locations);
        List<Location> retrievedLocations = locationService.findAll();
        assertEquals(locations, retrievedLocations);
        assertEquals(2, retrievedLocations.size());
    }

    @Test
    public void testLocationServiceFindById() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Santa Rita";
        Location location = LocationBuilder.aLocation().withName(name).build();
        when(locationRepository.existsById(name)).thenReturn(true);
        when(locationRepository.findById(name)).thenReturn(Optional.of(location));
        assertEquals(location, locationService.findById(name));
    }

    @Test
    public void testLocationServiceFindByIdForNonExistingLocation() {
        MockitoAnnotations.initMocks(this);
        String name = "Santa Rita";
        when(locationRepository.existsById(name)).thenReturn(false);
        try {
            locationService.findById(name);
        } catch (DataNotFoundException e) {
            String message = "Location " + name + " does not exists";
            assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void testLocationServiceCreateLocation() throws DataNotFoundException {
        MockitoAnnotations.initMocks(this);
        String name = "Santa Clara";
        String province = "Buenos Aires";
        int population = 2300;
        String state = "Sin iniciar";
        when(locationRepository.existsById(name)).thenReturn(false);
        Location createdLocation = locationService.createLocation(name, province, population, state);
        assertEquals(name, createdLocation.getName());
        assertEquals(province, createdLocation.getProvince());
        assertEquals(population, createdLocation.getPopulation());
        assertEquals(state, createdLocation.getState());
    }

    @Test
    public void testLocationServiceCreateLocationForAlreadyExistingLocation() {
        MockitoAnnotations.initMocks(this);
        String name = "Santa Clara";
        String province = "Buenos Aires";
        int population = 2300;
        String state = "Sin iniciar";
        when(locationRepository.existsById(name)).thenReturn(true);
        try {
            locationService.createLocation(name, province, population, state);
        } catch (DataNotFoundException e) {
            String message = "Location " + name + " already exists";
            assertEquals(message, e.getMessage());
        }
    }

}