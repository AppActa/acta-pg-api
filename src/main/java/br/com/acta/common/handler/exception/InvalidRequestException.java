package br.com.acta.common.handler.exception;

public class InvalidRequestException extends BusinessRuleException {
    public InvalidRequestException(String message) {
        super(message);
    }
}
