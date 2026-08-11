package br.com.acta.common.handler.exception;

public class ActiveEntityDeletionException extends RuntimeException {
    public ActiveEntityDeletionException(String entidade) {
        super("Não foi possível excluir " + entidade + " pois ela tem dependentes ativos");
    }
}
