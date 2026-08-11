package br.com.acta.entity.enums;

public enum StatusPlanoAcao {
    RASCUNHO, APROVADO, EM_EXECUCAO, CONCLUIDO, CANCELADO;

    public boolean podeAtualizarStatus(StatusPlanoAcao novoStatus){
        return switch (this) {
            case RASCUNHO -> novoStatus == StatusPlanoAcao.APROVADO || novoStatus == StatusPlanoAcao.CANCELADO;
            case APROVADO -> novoStatus == StatusPlanoAcao.EM_EXECUCAO || novoStatus == StatusPlanoAcao.CANCELADO;
            case EM_EXECUCAO -> novoStatus == StatusPlanoAcao.CONCLUIDO || novoStatus == StatusPlanoAcao.CANCELADO;
            case CONCLUIDO, CANCELADO -> false;
        };
    }
}
