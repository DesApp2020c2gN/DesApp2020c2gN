package ar.edu.unq.desapp.grupon022020.backenddesappapi.service;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.builder.LocationBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ARSATWebService {

    @Autowired
    private LocationService locationService;

    public void loadLocationsFromARSAT() {
        final String url = "http://prod.arsat.apim.junar.com/plan-federal-de-internet/v1/puntos/";
        try {
            String conectados_url = url + "conectados.json";
            String backupFile_1 = "./src/main/resources/conectados.json";
            List<Location> connectedLocations = getLocations(conectados_url, 3, 4, 6, 8, backupFile_1);
            for (Location location :connectedLocations){
                locationService.save(location);
            }
            String futuros_url = url + "futuros.json";
            String backupFile_2 = "./src/main/resources/futuros.json";
            List<Location> planningLocations = getLocations(futuros_url, 2, 3, 5, 7, backupFile_2);
            for (Location location :planningLocations){
                locationService.save(location);
            }
        }
        catch(IOException e) {
            // Keep loading fake data...
        }
    }

    public List<Location> getLocations(String url, int nameIndex, int provinceIndex, int populationIndex, int stateIndex, String backupFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        RestTemplate restTemplate = new RestTemplate();
        URI uri = URI.create(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("", headers);
        JsonNode root;
        try {
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, request, String.class);
            root = objectMapper.readTree(result.getBody());
        }
        catch (HttpServerErrorException | JsonProcessingException e) {
            FileReader file = new FileReader(backupFile);
            root = objectMapper.readTree(file);
        }
        Iterator<JsonNode> data = root.get("data").iterator();
        data.next();
        return this.buildLocations(data, nameIndex, provinceIndex, populationIndex, stateIndex);
    }

    private List<Location> buildLocations(Iterator<JsonNode> data, int nameIndex, int provinceIndex, int populationIndex, int stateIndex){
        List<Location> locations = new ArrayList<>();
        while(data.hasNext()) {
            JsonNode locationJSON = data.next();
            locations.add(LocationBuilder.aLocation().
                    withName(locationJSON.get(nameIndex).asText()).
                    withProvince(locationJSON.get(provinceIndex).asText()).
                    withPopulation(locationJSON.get(populationIndex).asInt()).
                    withState(locationJSON.get(stateIndex).asText()).
                    build());
        }
        return locations;
    }

}
