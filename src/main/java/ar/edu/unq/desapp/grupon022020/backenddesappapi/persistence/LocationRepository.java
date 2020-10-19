package ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Location;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface LocationRepository extends CrudRepository<Location, String> {

    boolean existsById(String name);

    Optional<Location> findById(String id);

    List<Location> findAll();
}
