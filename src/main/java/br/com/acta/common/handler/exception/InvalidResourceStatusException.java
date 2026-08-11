package br.com.acta.common.handler.exception;

import java.util.List;

public class InvalidResourceStatusException extends BusinessRuleException {
    public InvalidResourceStatusException(String operacao, String recurso, String statusAtual){
        super("Não é possível " + operacao + " em " + recurso + " enquanto o status atual for " + statusAtual);
    }

    public InvalidResourceStatusException(String operacao, String recurso, String statusAtual, String dependente){
        super("Não é possível " + operacao + " em " + recurso + " enquanto o status atual de " + dependente + " não for " + statusAtual);
    }

    public InvalidResourceStatusException(String recurso, List<String> statusList){
        super("Não é possível realizar a ação em " + recurso + " enquanto o status atual estiver entre " + String.join(", ", statusList));
    }
}
