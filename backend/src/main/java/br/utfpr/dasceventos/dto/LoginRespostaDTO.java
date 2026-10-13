package br.utfpr.dasceventos.dto;

public record LoginRespostaDTO(
        Long id,
        String nome,
        String email,
        String tipo
) {
}
