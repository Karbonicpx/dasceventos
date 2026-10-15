package br.utfpr.dasceventos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlunoCadastroDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String curso,
        @NotBlank String registroAcademico,
        String telefone,
        @NotBlank @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres") String senha
) {
}
