package br.com.acta.handler.exception;

public class ActiveEntityDeleteException extends RuntimeException {
    public ActiveEntityDeleteException(String message) {
        super(message);
    }
}
