package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
//@SequenceGenerator(name = "SEQ_LOCATION", sequenceName = "SEQUENCE_LOCATION", initialValue = 1, allocationSize = 1)
public class Location {

    //private static final long serialVersionUID = 1L;

    //@Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LOCATION")
    //private Integer id;

    @Id
    private String name;

    @Column
    private String province;

    @Column
    private int population;

    @Column
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
