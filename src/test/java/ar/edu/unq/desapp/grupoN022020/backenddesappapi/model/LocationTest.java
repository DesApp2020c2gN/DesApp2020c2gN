package ar.edu.unq.desapp.grupoN022020.backenddesappapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @Test
    public void test_Constructor(){
        String name = "name";
        String province = "province";
        int population = 1000;
        String state = "state";

        Location location = new Location(name, province, population, state);

        assertEquals(location.getName(), name);
        assertEquals(location.getProvince(), province);
        assertEquals(location.getPopulation(), population);
        assertEquals(location.getState(), state);
    }
}