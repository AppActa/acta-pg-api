package br.com.acta.common.handler.exception;

public class StatusUpdateException extends BusinessRuleException {
    public StatusUpdateException(String statusAtual, String statusNovo) {
        super("Não é possível atualizar o status de " + statusAtual + " para " + statusNovo);
    }
}
