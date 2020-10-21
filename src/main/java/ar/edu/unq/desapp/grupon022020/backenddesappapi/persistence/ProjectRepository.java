package ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface ProjectRepository extends CrudRepository<Project, String> {

    Optional<Project> findById(String id);

    List<Project> findAll();

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Project p WHERE p.location.name=?1 and p.finishDate>p.startDate and p.finishDate>?2")
    boolean existsOpenProject(String locationName, LocalDate now);

}
