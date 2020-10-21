package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorUserBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
public class InitServiceInMemory {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @Autowired
    private LocationService locationService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void initialize() {
        if (className.equals("org.h2.Driver")) {
            logger.warn("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() {
        Location location_1 = LocationBuilder.aLocation().withName("Santa Rita").withPopulation(1200).build();
        Location location_2 = LocationBuilder.aLocation().withName("Rio Tercero").withPopulation(710).build();
        Location location_3 = LocationBuilder.aLocation().withName("Puerto Iguazu").withPopulation(1530).build();
        Location location_4 = LocationBuilder.aLocation().withName("Cruz Azul").withPopulation(955).build();
        Location location_5 = LocationBuilder.aLocation().withName("Santa Clara").withPopulation(780).build();
        locationService.save(location_1);
        locationService.save(location_2);
        locationService.save(location_3);
        locationService.save(location_4);
        locationService.save(location_5);

        DonorUser donorUser_1 = DonorUserBuilder.aDonorUser().withNickname("juan123").withName("Juan J").withPassword("123").withMoney(BigDecimal.valueOf(9000)).build();
        DonorUser donorUser_2 = DonorUserBuilder.aDonorUser().withNickname("maria456").withName("Maria M").withPassword("456").withMoney(BigDecimal.valueOf(8000)).build();
        DonorUser donorUser_3 = DonorUserBuilder.aDonorUser().withNickname("fabian789").withName("Fabian F").withPassword("789").withMoney(BigDecimal.valueOf(7500)).build();
        DonorUser donorUser_4 = DonorUserBuilder.aDonorUser().withNickname("julia000").withName("Julia J").withPassword("000").withMoney(BigDecimal.valueOf(1300)).build();
        DonorUser donorUser_5 = DonorUserBuilder.aDonorUser().withNickname("oscar111").withName("Oscar O").withPassword("111").withMoney(BigDecimal.valueOf(2450)).build();
        userService.save(donorUser_1);
        userService.save(donorUser_2);
        userService.save(donorUser_3);
        userService.save(donorUser_4);
        userService.save(donorUser_5);

        Project project_1 = ProjectBuilder.aProject().withName("Conectando Santa Rita").withLocation(location_1).withFactor(50).withClosurePercentage(75).build();
        project_1.setStartDate(LocalDate.now().minusDays(20));
        project_1.setFinishDate(LocalDate.now());
        Project project_2 = ProjectBuilder.aProject().withName("Conectando Rio Tercero").withLocation(location_2).withFactor(70).withClosurePercentage(90).build();
        project_2.setStartDate(LocalDate.now().minusDays(30));
        project_2.setFinishDate(LocalDate.now());
        Project project_3 = ProjectBuilder.aProject().withName("Conectando Puerto Iguazu").withLocation(location_3).withFactor(120).withClosurePercentage(95).build();
        project_3.setStartDate(LocalDate.now().minusDays(40));
        project_3.setFinishDate(LocalDate.now().plusDays(10));
        projectService.save(project_1);
        projectService.save(project_2);
        projectService.save(project_3);

        try {
            donorUser_1.donate(BigDecimal.valueOf(1200), "This is my first donation!", project_1);
            donorUser_1.donate(BigDecimal.valueOf(200), "This is my second donation!", project_1);
            donorUser_1.donate(BigDecimal.valueOf(123), "This is my third donation!", project_1);
            donorUser_1.donate(BigDecimal.valueOf(300), "This is my fourth donation!", project_2);
            donorUser_1.donate(BigDecimal.valueOf(400), "This is my fifth donation!", project_2);
            donorUser_2.donate(BigDecimal.valueOf(500), "Cool!", project_1);
            donorUser_2.donate(BigDecimal.valueOf(2000), "Awesome!", project_1);
            donorUser_3.donate(BigDecimal.valueOf(666), "Whatever", project_3);
            donorUser_4.donate(BigDecimal.valueOf(120), "Good luck!", project_1);
            donorUser_5.donate(BigDecimal.valueOf(2300), "Good luck!", project_1);
            userService.save(donorUser_1);
            userService.save(donorUser_2);
            userService.save(donorUser_3);
            userService.save(donorUser_4);
            userService.save(donorUser_5);
        } catch (InvalidDonationException e) {
            e.printStackTrace();
        }
    }
}
