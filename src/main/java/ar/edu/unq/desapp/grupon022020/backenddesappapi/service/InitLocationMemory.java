package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

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
public class InitLocationMemory {

    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${pring.datasource.driverClassName:NONE}")
    private String className;

    @Autowired
    private LocationService locationService;

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
    }
}
