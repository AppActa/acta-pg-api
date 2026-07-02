package br.com.acta.dto.pdca.verificacao_resultado;

import br.com.acta.entity.enums.StatusVerificacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VerificacaoResultadoRequestDTO(
        @NotNull(message = "{validation.verificacaoResultado.status.notnull}")
        StatusVerificacao status,

        @NotBlank(message = "{validation.verificacaoResultado.resumo.notblank}")
        @Size(max = 1000, message = "{validation.verificacaoResultado.resumo.size}")
        String resumo,

        @Size(max = 1000, message = "{validation.verificacaoResultado.observacao.size}")
        String observacao
) {
}
