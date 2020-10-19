package ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface UserRepository extends CrudRepository<DonorUser, String> {

    boolean existsById(String name);

    Optional<DonorUser> findById(String id);

    List<DonorUser> findAll();

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM DonorUser u WHERE u.nickname=?1 and u.password=?2")
    boolean loginUser(String nickname, String password);

}
