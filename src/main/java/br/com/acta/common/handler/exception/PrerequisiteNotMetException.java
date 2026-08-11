package br.com.acta.common.handler.exception;

public class PrerequisiteNotMetException extends BusinessRuleException {
    public PrerequisiteNotMetException(String message) {
        super(message);
    }

    public PrerequisiteNotMetException(String operacao, String requisito){
        super("Não é possível " + operacao + " enquanto o requisito " + requisito + " não for atendido");
    }
}
