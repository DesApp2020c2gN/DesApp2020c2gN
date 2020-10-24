package ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface UserRepository extends CrudRepository<Donor, String> {

    boolean existsById(String name);

    Optional<Donor> findById(String id);

    List<Donor> findAll();

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Donor d WHERE d.nickname=?1 and d.password=?2")
    boolean loginUser(String nickname, String password);

}
