package br.com.acta.handler.exception;

public class ImmutableFieldException extends RuntimeException {
    public ImmutableFieldException(String campo) {
        super("Campo " + campo + " não pode ser alterado");
    }
}
