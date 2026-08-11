package br.com.acta.entity.enums;

public enum StatusTreinamento {
    PENDENTE, CONFIRMADO, CONCLUIDO, DISPENSADO, CANCELADO;

    public boolean podeAtualizarStatus(StatusTreinamento novoStatus) {
        return switch (this) {
            case PENDENTE -> true;
            case CONFIRMADO -> novoStatus == CONCLUIDO || novoStatus == CANCELADO;
            case CONCLUIDO, DISPENSADO, CANCELADO -> false;
        };
    }
}