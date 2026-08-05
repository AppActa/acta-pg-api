package br.com.acta.common.handler.exception;

public class UniqueViolationException extends RuntimeException {
    public UniqueViolationException(String campo) {
        super("Já existe um registro com o mesmo valor para o campo " + campo);
    }

    public UniqueViolationException(String tabelaUm, String tabelaDois) {
        super("O " + tabelaUm + " já está vinculado a este " + tabelaDois);
    }
}
