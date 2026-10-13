package br.utfpr.dasceventos.dto;

import br.utfpr.dasceventos.model.Atividade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalTime;

public record AtividadeDTO(
        Long id,
        @NotBlank String nome,
        String tema,
        @NotNull LocalTime horario,
        @NotNull @Positive Integer duracaoMinutos
) {
    public static AtividadeDTO fromEntity(Atividade a) {
        return new AtividadeDTO(a.getId(), a.getNome(), a.getTema(), a.getHorario(), a.getDuracaoMinutos());
    }
}
