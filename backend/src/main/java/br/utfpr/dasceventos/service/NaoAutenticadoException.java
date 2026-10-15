package br.utfpr.dasceventos.service;

/** Exceção lançada quando a requisição não traz um token de administrador válido. */
public class NaoAutenticadoException extends RuntimeException {
    public NaoAutenticadoException(String message) {
        super(message);
    }
}
