package br.com.acta.common.handler.exception;

public class StatusUpdateException extends RuntimeException {
    public StatusUpdateException(String statusUm, String statusDois) {
        super("Não é possível atualizar o status de " + statusUm + " para " + statusDois);
    }
}
