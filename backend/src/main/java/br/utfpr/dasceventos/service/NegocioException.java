package br.utfpr.dasceventos.service;

/** Exceção lançada quando uma regra de negócio é violada (ex.: e-mail duplicado, vagas esgotadas). */
public class NegocioException extends RuntimeException {
    public NegocioException(String message) {
        super(message);
    }
}
