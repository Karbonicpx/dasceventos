package br.utfpr.dasceventos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Token de sessão de um administrador. Cada login gera um registro próprio, para que entrar
 * em outro dispositivo/navegador (ou em testes) não derrube as sessões já abertas em outros.
 */
@Entity
@Table(name = "administrador_token")
@Getter
@Setter
public class AdministradorToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 64)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrador_id", nullable = false)
    private Administrador administrador;

    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    @PrePersist
    public void prePersist() {
        this.criadoEm = LocalDateTime.now();
    }
}
