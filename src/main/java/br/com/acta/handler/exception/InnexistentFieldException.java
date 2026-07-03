package br.com.acta.handler.exception;

public class InnexistentFieldException extends RuntimeException {
    public InnexistentFieldException(String message) {
        super(message);
    }
}
