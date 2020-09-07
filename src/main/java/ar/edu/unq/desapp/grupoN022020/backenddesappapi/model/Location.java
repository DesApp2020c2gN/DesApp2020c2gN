package ar.edu.unq.desapp.grupoN022020.backenddesappapi.model;

public class Location {

    private String name;
    private String province;
    private int population;
    private String state;

    public Location(String name, String province, int population, String state){
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
