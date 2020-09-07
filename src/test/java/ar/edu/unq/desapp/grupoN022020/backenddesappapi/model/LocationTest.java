package ar.edu.unq.desapp.grupoN022020.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import static ar.edu.unq.desapp.grupoN022020.backenddesappapi.model.LocationBuilder.aLocation;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LocationTest {

    @Test
    public void testLocationName() {
        String name = "Piedras";
        Location location = aLocation().withName(name).build();

        assertEquals(location.getName(), name);
    }

    @Test
    public void testLocationProvince() {
        String province = "Catamarca";
        Location location = aLocation().withProvince(province).build();

        assertEquals(location.getProvince(), province);
    }

    @Test
    public void testLocationPopulation() {
        int population = 1250;
        Location location = aLocation().withPopulation(population).build();

        assertEquals(location.getPopulation(), population);
    }

    @Test
    public void testLocationState() {
        String state = "Conectado";
        Location location = aLocation().withState(state).build();

        assertEquals(location.getState(), state);
    }
}