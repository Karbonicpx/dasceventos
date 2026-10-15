package br.utfpr.dasceventos.dto;

public record LoginRespostaDTO(
        Long id,
        String nome,
        String email,
        String tipo,
        String papel,
        String fotoPerfil,
        String token
) {
}
