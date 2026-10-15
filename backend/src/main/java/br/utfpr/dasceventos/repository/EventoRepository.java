package br.utfpr.dasceventos.repository;

import br.utfpr.dasceventos.model.CategoriaEvento;
import br.utfpr.dasceventos.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByDataGreaterThanEqualOrderByDataAscHorarioAsc(LocalDate data);
    List<Evento> findByCategoria(CategoriaEvento categoria);
    List<Evento> findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String titulo, String descricao);
}
