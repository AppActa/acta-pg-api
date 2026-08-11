package br.com.acta.common.handler.exception;

public class ForbiddenOperationException extends BusinessRuleException {
    public ForbiddenOperationException(String operacao) {
        super("O usuário não possui permissão para " + operacao);
    }

    public ForbiddenOperationException(){
        super("O usuário não possui permissão para realizar esta operação");
    }
}
