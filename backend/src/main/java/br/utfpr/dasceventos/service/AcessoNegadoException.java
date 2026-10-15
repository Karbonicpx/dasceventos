package br.utfpr.dasceventos.service;

/** Exceção lançada quando o administrador autenticado não tem papel suficiente para a ação. */
public class AcessoNegadoException extends RuntimeException {
    public AcessoNegadoException(String message) {
        super(message);
    }
}
