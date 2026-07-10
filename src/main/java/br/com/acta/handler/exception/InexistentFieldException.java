package br.com.acta.handler.exception;

public class InexistentFieldException extends RuntimeException {
    public InexistentFieldException(String campo) {
        super("Campo " + campo + " inexistente");
    }
}
