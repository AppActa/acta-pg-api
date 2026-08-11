package br.com.acta.common.handler.exception;

public class ResourceInUseException extends BusinessRuleException {
    public ResourceInUseException(String recurso, String dependencia) {
        super("Não é possível remover " + recurso + " porque está em uso por " + dependencia);
    }

    public ResourceInUseException(String operacao, String recurso, String motivo) {
        super("Não é possível " + operacao + " " + recurso + " porque " + motivo);
    }
}
