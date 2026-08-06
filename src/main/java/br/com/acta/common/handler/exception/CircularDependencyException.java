package br.com.acta.common.handler.exception;

public class CircularDependencyException extends BusinessRuleException {
    public CircularDependencyException(String recurso) {
        super("A dependência informada criaria um ciclo entre " + recurso);
    }

    public CircularDependencyException(String primeiroRecurso, String segundoRecurso){
        super("A dependência entre " + primeiroRecurso + " e " + segundoRecurso + " criaria um ciclo");
    }
}
