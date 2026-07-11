package br.com.acta.handler.exception;

public class RegexException extends RuntimeException {
    public RegexException(String message) {
        super("O " + message + " digitado é inválido");
    }
}
