package br.utfpr.dasceventos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "administrador")
@Getter
@Setter
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 120)
    private String nome;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank
    @Column(name = "registro_academico", nullable = false, unique = true, length = 20)
    private String registroAcademico;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PapelAdministrador papel = PapelAdministrador.DIRETORIA;

    @Column(nullable = false)
    private boolean aprovado = false;

    @Column(name = "foto_perfil", columnDefinition = "LONGTEXT")
    private String fotoPerfil;
}
