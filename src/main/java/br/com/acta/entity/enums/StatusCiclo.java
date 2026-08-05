package br.com.acta.entity.enums;

public enum StatusCiclo {
    PLANEJAMENTO, EXECUCAO, VERIFICACAO, PADRONIZACAO, CONCLUIDO, CANCELADO, PAUSADO;

    public boolean podeAtualizarStatus(StatusCiclo novoStatus) {
        return switch (this) {
            case PLANEJAMENTO -> novoStatus == EXECUCAO || novoStatus == PAUSADO || novoStatus == CANCELADO;
            case EXECUCAO -> novoStatus == VERIFICACAO || novoStatus == PAUSADO || novoStatus == CANCELADO;
            case VERIFICACAO -> novoStatus == PADRONIZACAO || novoStatus == PAUSADO || novoStatus == CANCELADO;
            case PADRONIZACAO -> novoStatus == PLANEJAMENTO || novoStatus == PAUSADO || novoStatus == CANCELADO;
            case PAUSADO -> novoStatus == PLANEJAMENTO || novoStatus == EXECUCAO || novoStatus == VERIFICACAO || novoStatus == PADRONIZACAO || novoStatus == CANCELADO;
            case CONCLUIDO, CANCELADO -> false;
        };
    }
}
