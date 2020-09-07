package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

public class LocationBuilder {

    private String name = "default_name";
    private String province = "default_province";
    private int population = 1;
    private String state = "default_state";

    public static LocationBuilder aLocation() {
        return new LocationBuilder();
    }

    public Location build() {
        Location newLocation = new Location(name, province, population, state);
        return newLocation;
    }

    public LocationBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public LocationBuilder withProvince(String province) {
        this.province = province;
        return this;
    }

    public LocationBuilder withPopulation(int population) {
        this.population = population;
        return this;
    }

    public LocationBuilder withState(String state) {
        this.state = state;
        return this;
    }
}
