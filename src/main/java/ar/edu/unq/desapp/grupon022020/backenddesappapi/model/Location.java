package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
public class Location {

    @Id
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Column
    @NotBlank(message = "Province cannot be blanck")
    private String province;

    @Column
    @Positive(message = "Population should be positive")
    private int population;

    @Column
    @NotBlank(message = "State cannot be blank")
    private String state;

    public Location() {}

    public Location(String name, String province, int population, String state) {
        this.name = name;
        this.province = province;
        this.population = population;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
