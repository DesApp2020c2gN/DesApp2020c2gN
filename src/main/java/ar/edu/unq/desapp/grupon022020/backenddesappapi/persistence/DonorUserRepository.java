package ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface DonorUserRepository extends CrudRepository<DonorUser, String> {

    Optional<DonorUser> findById(String id);

    List<DonorUser> findAll();

}
