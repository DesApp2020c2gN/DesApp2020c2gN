package ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testLocationRepositoryExistsById() {
        String name = "Santa Clara";
        Location location = LocationBuilder.aLocation().withName(name).build();
        entityManager.persist(location);
        entityManager.flush();
        boolean exists = locationRepository.existsById(name);
        assertEquals(true, exists);
    }

    @Test
    public void testLocationRepositoryFindById() {
        String name = "Catalinas";
        Location location = LocationBuilder.aLocation().withName(name).build();
        entityManager.persist(location);
        entityManager.flush();
        Location found = locationRepository.findById(name).get();
        assertEquals(location, found);
    }

    @Test
    public void testLocationRepositoryFindAll() {
        String name_1 = "Catalinas";
        Location location_1 = LocationBuilder.aLocation().withName(name_1).build();
        String name_2 = "Santa Clara";
        Location location_2 = LocationBuilder.aLocation().withName(name_2).build();
        entityManager.persist(location_1);
        entityManager.persist(location_2);
        entityManager.flush();
        List<Location> locationsFound = locationRepository.findAll();
        assertEquals(2, locationsFound.size());
        assertTrue(locationsFound.contains(location_1));
        assertTrue(locationsFound.contains(location_2));
    }
}
