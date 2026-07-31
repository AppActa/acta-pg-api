package br.com.acta.dto.pdca.verificacao_resultado;

import br.com.acta.config.swagger.SwaggerExamples;
import br.com.acta.entity.enums.StatusVerificacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VerificacaoResultadoRequestDTO(
        @Schema(description = "Status da verificação", example = SwaggerExamples.STATUS_VERIFICACAO)
        @NotNull(message = "{validation.verificacaoResultado.status.notnull}")
        StatusVerificacao status,

        @Schema(description = "Resumo da verificação", example = SwaggerExamples.RESUMO_VERIFICACAO)
        @NotBlank(message = "{validation.verificacaoResultado.resumo.notblank}")
        @Size(max = 1000, message = "{validation.verificacaoResultado.resumo.size}")
        String resumo,

        @Schema(description = "Observação da verificação", example = SwaggerExamples.OBSERVACAO_VERIFICACAO)
        @Size(max = 1000, message = "{validation.verificacaoResultado.observacao.size}")
        String observacao
) {
}
