package br.utfpr.dasceventos.dto;

import br.utfpr.dasceventos.model.Administrador;
import br.utfpr.dasceventos.model.PapelAdministrador;

public record AdministradorRespostaDTO(
        Long id,
        String nome,
        String email,
        String registroAcademico,
        PapelAdministrador papel,
        boolean aprovado,
        String fotoPerfil
) {
    public static AdministradorRespostaDTO fromEntity(Administrador administrador) {
        return new AdministradorRespostaDTO(
                administrador.getId(),
                administrador.getNome(),
                administrador.getEmail(),
                administrador.getRegistroAcademico(),
                administrador.getPapel(),
                administrador.isAprovado(),
                administrador.getFotoPerfil()
        );
    }
}
