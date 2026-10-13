package br.utfpr.dasceventos.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "inscricao", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"aluno_id", "evento_id"})
})
@Getter
@Setter
public class Inscricao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(name = "data_inscricao", nullable = false)
    private LocalDateTime dataInscricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusInscricao status;

    @PrePersist
    public void prePersist() {
        this.dataInscricao = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusInscricao.CONFIRMADA;
        }
    }
}
