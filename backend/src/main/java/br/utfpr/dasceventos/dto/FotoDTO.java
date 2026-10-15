package br.utfpr.dasceventos.dto;

import jakarta.validation.constraints.NotBlank;

public record FotoDTO(
        @NotBlank String fotoPerfil
) {
}
