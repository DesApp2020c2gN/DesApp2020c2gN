package ar.edu.unq.desapp.grupon022020.backenddesappapi.persistence;

import ar.edu.unq.desapp.grupon022020.backenddesappapi.model.DonorUser;
import org.springframework.data.jpa.repository.Query;

public interface CustomizedUserRepository {

    @Query("SELECT u FROM DonorUser u WHERE u.nickname=?1 and u.password=?2")
    DonorUser loginUser(String nickname, String password);
}
