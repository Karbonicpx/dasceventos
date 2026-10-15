package br.utfpr.dasceventos.repository;

import br.utfpr.dasceventos.model.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Optional<Administrador> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByRegistroAcademico(String registroAcademico);
    List<Administrador> findByAprovadoFalse();
}
