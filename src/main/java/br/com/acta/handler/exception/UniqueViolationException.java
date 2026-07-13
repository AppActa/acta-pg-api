package br.com.acta.handler.exception;

public class UniqueViolationException extends RuntimeException {
    public UniqueViolationException(String campo) {
        super("Já existe um registro com o mesmo valor para o campo " + campo);
    }
}
