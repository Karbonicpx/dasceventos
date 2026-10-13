package br.utfpr.dasceventos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "evento")
@Getter
@Setter
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 2000)
    private String descricao;

    @NotNull
    @Column(nullable = false)
    private LocalDate data;

    @NotNull
    @Column(nullable = false)
    private LocalTime horario;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String local;

    @NotNull
    @PositiveOrZero
    @Column(name = "vagas_totais", nullable = false)
    private Integer vagasTotais;

    @NotNull
    @PositiveOrZero
    @Column(name = "vagas_disponiveis", nullable = false)
    private Integer vagasDisponiveis;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private CategoriaEvento categoria;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Atividade> atividades = new ArrayList<>();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscricao> inscricoes = new ArrayList<>();
}
