package br.com.acta.entity.enums;

public enum StatusProblema {
    ABERTO, EM_ANALISE, PRIORIZADO, RESOLVIDO, DESCARTADO;

    public boolean podeAtualizarStatus(StatusProblema novoStatus){
        return switch (this){
            case ABERTO -> novoStatus == EM_ANALISE || novoStatus == DESCARTADO;
            case EM_ANALISE -> novoStatus == PRIORIZADO || novoStatus == DESCARTADO;
            case PRIORIZADO -> novoStatus == RESOLVIDO || novoStatus == DESCARTADO;
            case RESOLVIDO, DESCARTADO -> false;
        };
    }
}
