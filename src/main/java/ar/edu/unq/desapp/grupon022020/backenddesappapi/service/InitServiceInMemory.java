package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.ProjectStatus;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonorBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.ProjectBuilder;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.exceptions.InvalidDonationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private ARSATWebService webService;
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
        webService.loadLocationsFromARSAT();

        Location location_1 = LocationBuilder.aLocation().withName("Santa Rita").withPopulation(1200).withProvince("Misiones").withState("En Planificación").build();
        Location location_2 = LocationBuilder.aLocation().withName("Rio Tercero").withPopulation(710).withProvince("Cordoba").withState("En Planificación").build();
        Location location_3 = LocationBuilder.aLocation().withName("Puerto Iguazu").withPopulation(1530).withProvince("Misiones").withState("En Planificación").build();
        Location location_4 = LocationBuilder.aLocation().withName("Cipolletti").withPopulation(955).withProvince("Rio Negro").withState("En Planificación").build();
        Location location_5 = LocationBuilder.aLocation().withName("Santa Clara").withPopulation(780).withProvince("Buenos Aires").withState("En Planificación").build();
        Location location_6 = LocationBuilder.aLocation().withName("Mercedes").withPopulation(2400).withProvince("Buenos Aires").withState("En Planificación").build();
        Location location_7 = LocationBuilder.aLocation().withName("Los Robles").withPopulation(350).withProvince("La Rioja").withState("En Planificación").build();
        Location location_8 = LocationBuilder.aLocation().withName("Lago Puelo").withPopulation(654).withProvince("Chubut").withState("En Planificación").build();
        Location location_9 = LocationBuilder.aLocation().withName("Trevelin").withPopulation(580).withProvince("Chubut").withState("En Planificación").build();
        Location location_10 = LocationBuilder.aLocation().withName("General Campos").withPopulation(1180).withProvince("Entre Rios").withState("En Planificación").build();

        locationService.save(location_1);
        locationService.save(location_2);
        locationService.save(location_3);
        locationService.save(location_4);
        locationService.save(location_5);
        locationService.save(location_6);
        locationService.save(location_7);
        locationService.save(location_8);
        locationService.save(location_9);
        locationService.save(location_10);

        Donor donor_1 = DonorBuilder.aDonorUser().withNickname("juan123").withName("Juan J").withMail("argentinaconectada2020@gmail.com").withPassword("123").withMoney(BigDecimal.valueOf(9000)).build();
        Donor donor_2 = DonorBuilder.aDonorUser().withNickname("maria456").withName("Maria M").withMail("argentinaconectada2020@gmail.com").withPassword("456").withMoney(BigDecimal.valueOf(8000)).build();
        Donor donor_3 = DonorBuilder.aDonorUser().withNickname("fabian789").withName("Fabian F").withMail("argentinaconectada2020@gmail.com").withPassword("789").withMoney(BigDecimal.valueOf(7500)).build();
        Donor donor_4 = DonorBuilder.aDonorUser().withNickname("julia000").withName("Julia J").withMail("argentinaconectada2020@gmail.com").withPassword("000").withMoney(BigDecimal.valueOf(3300)).build();
        Donor donor_5 = DonorBuilder.aDonorUser().withNickname("oscar111").withName("Oscar O").withMail("argentinaconectada2020@gmail.com").withPassword("111").withMoney(BigDecimal.valueOf(2450)).build();
        userService.save(donor_1);
        userService.save(donor_2);
        userService.save(donor_3);
        userService.save(donor_4);
        userService.save(donor_5);

        Project project_1 = ProjectBuilder.aProject().withName("Conectando Santa Rita").withLocation(location_1).withFactor(50).withClosurePercentage(75).build();
        project_1.setStartDate(LocalDate.now().minusDays(20));
        project_1.setFinishDate(LocalDate.now());
        Project project_2 = ProjectBuilder.aProject().withName("El futuro de Rio Tercero").withLocation(location_2).withFactor(70).withClosurePercentage(90).build();
        project_2.setStartDate(LocalDate.now().minusDays(30));
        project_2.setFinishDate(LocalDate.now());
        Project project_3 = ProjectBuilder.aProject().withName("Vamos Puerto Iguazu").withLocation(location_3).withFactor(3).withClosurePercentage(95).build();
        project_3.setStartDate(LocalDate.now().minusDays(40));
        project_3.setFinishDate(LocalDate.now().plusDays(60));
        Project project_4 = ProjectBuilder.aProject().withName("Yo ayudo a Cipolletti").withLocation(location_4).withFactor(17).withClosurePercentage(80).build();
        project_4.setStartDate(LocalDate.now().plusDays(50));
        project_4.setFinishDate(LocalDate.now().plusDays(130));
        Project project_5 = ProjectBuilder.aProject().withName("Todos con Santa Clara").withLocation(location_5).withFactor(24).withClosurePercentage(77).build();
        project_5.setStartDate(LocalDate.now().minusDays(130));
        project_5.setFinishDate(LocalDate.now().minusDays(44));
        project_5.setStatus(ProjectStatus.INCOMPLETE.name());
        Project project_6 = ProjectBuilder.aProject().withName("Mercedes 2.0").withLocation(location_6).withFactor(31).withClosurePercentage(85).build();
        project_6.setStartDate(LocalDate.now().minusDays(180));
        project_6.setFinishDate(LocalDate.now().minusDays(3));
        project_6.setStatus(ProjectStatus.CANCELLED.name());
        Project project_7 = ProjectBuilder.aProject().withName("Conectemos Los Robles").withLocation(location_7).withFactor(26).withClosurePercentage(70).build();
        project_7.setStartDate(LocalDate.now().minusDays(50));
        project_7.setFinishDate(LocalDate.now().minusDays(30));
        project_7.setStatus(ProjectStatus.INCOMPLETE.name());
        Project project_8 = ProjectBuilder.aProject().withName("Lago Puelo esta esperandote").withLocation(location_8).withFactor(12).withClosurePercentage(40).build();
        project_8.setStartDate(LocalDate.now().minusDays(70));
        project_8.setFinishDate(LocalDate.now().minusDays(40));
        project_8.setStatus(ProjectStatus.COMPLETE.name());
        Project project_9 = ProjectBuilder.aProject().withName("Fibra optica en Trevelin").withLocation(location_9).withFactor(14).withClosurePercentage(53).build();
        project_9.setStartDate(LocalDate.now().minusDays(20));
        project_9.setFinishDate(LocalDate.now().plusDays(20));
        project_9.setStatus(ProjectStatus.COMPLETE.name());
        Project project_10 = ProjectBuilder.aProject().withName("General Campos conectadisimo").withLocation(location_10).withFactor(10).withClosurePercentage(39).build();
        project_10.setStartDate(LocalDate.now().minusDays(50));
        project_10.setFinishDate(LocalDate.now().plusDays(67));
        projectService.save(project_1);
        projectService.save(project_2);
        projectService.save(project_3);
        projectService.save(project_4);
        projectService.save(project_5);
        projectService.save(project_6);
        projectService.save(project_7);
        projectService.save(project_8);
        projectService.save(project_9);
        projectService.save(project_10);

        try {
            donor_1.donate(BigDecimal.valueOf(1200), "Esta es mi primera donación!", project_1);
            donor_1.donate(BigDecimal.valueOf(200), "Esta es mi segunda donación!", project_1);
            donor_1.donate(BigDecimal.valueOf(123), "Esta es mi tercer donación!", project_1);
            donor_1.donate(BigDecimal.valueOf(300), "Esta es mi cuarta donación!", project_2);
            donor_1.donate(BigDecimal.valueOf(400), "Esta es mi quinta donación!", project_2);
            donor_2.donate(BigDecimal.valueOf(500), "Vamos!", project_1);
            donor_2.donate(BigDecimal.valueOf(2000), "Ayudando un poco...", project_1);
            donor_3.donate(BigDecimal.valueOf(666), "Pronto donaré más", project_3);
            donor_4.donate(BigDecimal.valueOf(120), "Buena suerte!", project_1);
            donor_5.donate(BigDecimal.valueOf(2300), "Buena suerte para todos!", project_1);
            userService.save(donor_1);
            userService.save(donor_2);
            userService.save(donor_3);
            userService.save(donor_4);
            userService.save(donor_5);
        } catch (InvalidDonationException e) {
            e.printStackTrace();
        }
    }
}
