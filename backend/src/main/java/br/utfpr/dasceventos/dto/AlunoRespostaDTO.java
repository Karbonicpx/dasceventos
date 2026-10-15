package br.utfpr.dasceventos.dto;

import br.utfpr.dasceventos.model.Aluno;

import java.time.LocalDateTime;

public record AlunoRespostaDTO(
        Long id,
        String nome,
        String email,
        String curso,
        String registroAcademico,
        String telefone,
        String fotoPerfil,
        LocalDateTime dataCadastro
) {
    public static AlunoRespostaDTO fromEntity(Aluno aluno) {
        return new AlunoRespostaDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCurso(),
                aluno.getRegistroAcademico(),
                aluno.getTelefone(),
                aluno.getFotoPerfil(),
                aluno.getDataCadastro()
        );
    }
}
