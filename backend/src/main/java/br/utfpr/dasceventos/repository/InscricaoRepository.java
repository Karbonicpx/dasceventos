package br.utfpr.dasceventos.repository;

import br.utfpr.dasceventos.model.Inscricao;
import br.utfpr.dasceventos.model.StatusInscricao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    List<Inscricao> findByAlunoId(Long alunoId);
    List<Inscricao> findByEventoIdAndStatus(Long eventoId, StatusInscricao status);
    Optional<Inscricao> findByAlunoIdAndEventoId(Long alunoId, Long eventoId);
    boolean existsByAlunoIdAndEventoIdAndStatus(Long alunoId, Long eventoId, StatusInscricao status);
}
