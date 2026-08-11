package br.com.acta.common.handler.exception;

public class InvalidRelationshipException extends RuntimeException {
    public InvalidRelationshipException(String primeiroRecurso, String segundoRecurso, String relacionamentoEsperado) {
        super(primeiroRecurso + " e " + segundoRecurso + " devem " + relacionamentoEsperado + " para realizar a operação");
    }

    public InvalidRelationshipException(String relacionamentoEsperado){
        super("Os dois elementos devem " + relacionamentoEsperado + " para realizar a operação");
    }
}
