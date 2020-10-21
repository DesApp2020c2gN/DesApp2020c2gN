package ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.Donation;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Configuration
@Repository
public interface DonationRepository extends CrudRepository<Donation, Integer> {

    Optional<Donation> findById(Integer id);

    List<Donation> findAll();

    @Transactional
    @Modifying
    @Query("DELETE FROM Donation d WHERE d.id=?1")
    void deleteDonation(Integer id);
}
