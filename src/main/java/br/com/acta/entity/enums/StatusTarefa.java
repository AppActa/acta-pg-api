package br.com.acta.entity.enums;

public enum StatusTarefa {
    PENDENTE, EM_ANDAMENTO, BLOQUEADA, CONCLUIDA, ATRASADA, CANCELADA;

    public boolean podeAtualizarStatus(StatusTarefa novoStatus){
        return switch (this) {
            case PENDENTE, BLOQUEADA -> novoStatus == EM_ANDAMENTO || novoStatus == CANCELADA;
            case EM_ANDAMENTO -> novoStatus == BLOQUEADA || novoStatus == CONCLUIDA || novoStatus == ATRASADA || novoStatus == CANCELADA;
            case ATRASADA -> novoStatus == EM_ANDAMENTO || novoStatus == CONCLUIDA || novoStatus == CANCELADA;
            case CONCLUIDA, CANCELADA -> false;
        };
    }
}