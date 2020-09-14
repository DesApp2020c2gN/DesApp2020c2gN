package ar.edu.unq.desapp.grupon022020.backenddesappapi.model;

import java.time.LocalDate;
import java.util.Calendar;

public class DonationBuilder {

    private User user = UserBuilder.aUser().build();
    private Project project = ProjectBuilder.aProject().build();
    private Integer amount = 1000;
    private String comment = "Default comment";
    private LocalDate date = LocalDate.now();;
}
