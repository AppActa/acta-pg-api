package br.com.acta.dto.join.priorizacao_problema;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PriorizacaoProblemaRequestDTO(
        @Schema(description = "ID do usuário", example = SwaggerRequestExamples.ID_USUARIO)
        @NotNull(message = "{validation.idUsuario.notnull}")
        @Positive(message = "{validation.idUsuario.positive}")
        Long idUsuario,

        @Schema(description = "Posição da priorização", example = SwaggerRequestExamples.POSICAO)
        @NotNull(message = "{validation.priorizacao.posicao.notnull}")
        @Positive(message = "{validation.priorizacao.posicao.positive}")
        Integer posicao,

        @Schema(description = "Peso calculado", example = SwaggerRequestExamples.PESO_CALCULADO)
        @NotNull(message = "{validation.peso.notnull}")
        @Positive(message = "{validation.peso.positive}")
        @Digits(integer = 3, fraction = 2, message = "{validation.peso.digits}")
        BigDecimal pesoCalculado
) {
}
