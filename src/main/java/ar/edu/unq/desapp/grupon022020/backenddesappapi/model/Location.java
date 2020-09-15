package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

public class Location {

    private final String name;
    private final String province;
    private final int population;
    private final String state;

    public Location(String name, String province, int population, String state) {
        this.name = name;
        this.province = province;
        this.population = population;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public int getPopulation() {
        return population;
    }

    public String getState() {
        return state;
    }
}
