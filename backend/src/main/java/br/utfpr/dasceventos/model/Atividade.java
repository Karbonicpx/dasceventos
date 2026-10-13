package br.utfpr.dasceventos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "atividade")
@Getter
@Setter
public class Atividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String nome;

    @Column(length = 150)
    private String tema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @NotNull
    @Column(nullable = false)
    private LocalTime horario;

    @Positive
    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;
}
