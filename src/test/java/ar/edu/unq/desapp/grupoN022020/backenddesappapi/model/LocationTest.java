package ar.edu.unq.desapp.grupoN022020.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import static ar.edu.unq.desapp.grupoN022020.backenddesappapi.model.LocationBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    public void test_LocationName(){
        String name = "Piedras";
        Location location = aLocation().withName(name).build();

        assertEquals(location.getName(), name);
    }

    @Test
    public void test_LocationProvince(){
        String province = "Catamarca";
        Location location = aLocation().withProvince(province).build();

        assertEquals(location.getProvince(), province);
    }

    @Test
    public void test_LocationPopulation(){
        int population = 1250;
        Location location = aLocation().withPopulation(population).build();

        assertEquals(location.getPopulation(), population);
    }

    @Test
    public void test_LocationState(){
        String state = "Conectado";
        Location location = aLocation().withState(state).build();

        assertEquals(location.getState(), state);
    }
}