package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void testLocationServiceExistsById() {
        MockitoAnnotations.initMocks(this);
        String name_1 = "Santa Rita";
        String name_2 = "Colon";
        when(locationRepository.existsById(name_1)).thenReturn(true);
        when(locationRepository.existsById(name_2)).thenReturn(false);
        assertTrue(locationService.existsById(name_1));
        assertFalse(locationService.existsById(name_2));
    }

    @Test
    public void testLocationServiceFindById() {
        MockitoAnnotations.initMocks(this);
        String name = "Santa Rita";
        Location location = LocationBuilder.aLocation().withName(name).build();
        when(locationRepository.findById(name)).thenReturn(Optional.of(location));
        assertEquals(location, locationService.findById(name));
    }

}