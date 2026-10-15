package br.utfpr.dasceventos.repository;

import br.utfpr.dasceventos.model.AdministradorToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdministradorTokenRepository extends JpaRepository<AdministradorToken, Long> {

    @Query("SELECT t FROM AdministradorToken t JOIN FETCH t.administrador WHERE t.token = :token")
    Optional<AdministradorToken> findByToken(@Param("token") String token);

    void deleteByToken(String token);
}
