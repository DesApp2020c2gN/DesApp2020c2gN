package ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Project;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends CrudRepository<Project, Integer> {

    Optional<Project> findById(Integer id);

    List<Project> findAll();
}
