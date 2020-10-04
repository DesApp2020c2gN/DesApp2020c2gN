package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.DonationBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Service
@Transactional
public class InitServiceInMemory {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${spring.datasource.driverClassName:NONE}")
    private String className;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DonationService donationService;

    @PostConstruct
    public void initialize() {
        if (className.equals("org.h2.Driver")) {
            logger.warn("Init Data Using H2 DB");
            fireInitialData();
        }
    }

    private void fireInitialData() {
        Location location_1 = LocationBuilder.aLocation().withName("Santa Rita").build();
        locationService.save(location_1);
        Location location_2 = LocationBuilder.aLocation().withName("Rio Tercero").build();
        locationService.save(location_2);
        Location location_3 = LocationBuilder.aLocation().withName("Puerto Iguazu").build();
        locationService.save(location_3);

        Donation donation_1 = DonationBuilder.aDonation().withDonorNickname("juan123").build();
        donationService.save(donation_1);
        Donation donation_2 = DonationBuilder.aDonation().withDonorNickname("maria456").build();
        donationService.save(donation_2);
        Donation donation_3 = DonationBuilder.aDonation().withDonorNickname("dani900").build();
        donationService.save(donation_3);
    }
}
