package br.utfpr.dasceventos.dto;

import br.utfpr.dasceventos.model.CategoriaEvento;
import br.utfpr.dasceventos.model.Evento;
import br.utfpr.dasceventos.model.StatusEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;
import java.time.LocalTime;

public record EventoDTO(
        Long id,
        @NotBlank String titulo,
        String descricao,
        @NotNull LocalDate data,
        @NotNull LocalTime horario,
        @NotBlank String local,
        @NotNull @PositiveOrZero Integer vagasTotais,
        Integer vagasDisponiveis,
        @NotNull CategoriaEvento categoria,
        StatusEvento status,
        String fotoBanner1,
        String fotoBanner2,
        String fotoBanner3
) {
    public static EventoDTO fromEntity(Evento e) {
        return new EventoDTO(
                e.getId(), e.getTitulo(), e.getDescricao(), e.getData(), e.getHorario(),
                e.getLocal(), e.getVagasTotais(), e.getVagasDisponiveis(), e.getCategoria(), e.getStatus(),
                e.getFotoBanner1(), e.getFotoBanner2(), e.getFotoBanner3()
        );
    }
}
