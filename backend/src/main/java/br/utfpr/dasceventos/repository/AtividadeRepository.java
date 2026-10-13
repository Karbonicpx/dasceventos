package br.utfpr.dasceventos.repository;

import br.utfpr.dasceventos.model.Atividade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtividadeRepository extends JpaRepository<Atividade, Long> {
    List<Atividade> findByEventoIdOrderByHorarioAsc(Long eventoId);
}
