package br.utfpr.dasceventos.dto;

import br.utfpr.dasceventos.model.Inscricao;
import br.utfpr.dasceventos.model.StatusInscricao;

import java.time.LocalDateTime;

public record InscricaoDTO(
        Long id,
        Long alunoId,
        String alunoNome,
        String alunoRegistroAcademico,
        String alunoFotoPerfil,
        Long eventoId,
        String eventoTitulo,
        LocalDateTime dataInscricao,
        StatusInscricao status
) {
    public static InscricaoDTO fromEntity(Inscricao i) {
        return new InscricaoDTO(
                i.getId(),
                i.getAluno().getId(),
                i.getAluno().getNome(),
                i.getAluno().getRegistroAcademico(),
                i.getAluno().getFotoPerfil(),
                i.getEvento().getId(),
                i.getEvento().getTitulo(),
                i.getDataInscricao(),
                i.getStatus()
        );
    }
}
