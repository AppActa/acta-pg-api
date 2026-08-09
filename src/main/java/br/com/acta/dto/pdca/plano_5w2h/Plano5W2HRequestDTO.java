package br.com.acta.dto.pdca.plano_5w2h;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Plano5W2HRequestDTO(
        @Schema(description = "O que será feito", example = SwaggerRequestExamples.WHAT_ACAO)
        @NotBlank(message = "{validation.plano5w2h.whatAcao.notblank}")
        @Size(max = 1000, message = "{validation.plano5w2h.whatAcao.size}")
        String whatAcao,

        @Schema(description = "Por que será feito", example = SwaggerRequestExamples.WHY_JUSTIFICATIVA)
        @NotBlank(message = "{validation.plano5w2h.whyJustificativa.notblank}")
        @Size(max = 1000, message = "{validation.plano5w2h.whyJustificativa.size}")
        String whyJustificativa,

        @Schema(description = "Onde será feito", example = SwaggerRequestExamples.WHERE_LOCAL)
        @NotBlank(message = "{validation.plano5w2h.whereLocal.notblank}")
        @Size(max = 1000, message = "{validation.plano5w2h.whereLocal.size}")
        String whereLocal,

        @Schema(description = "Quando começará", example = SwaggerRequestExamples.WHEN_INICIO)
        @FutureOrPresent(message = "{validation.plano5w2h.whenInicio.futureorpresent}")
        LocalDate whenInicio,

        @Schema(description = "Quando será concluído", example = SwaggerRequestExamples.WHEN_FIM)
        @NotNull(message = "{validation.plano5w2h.whenFim.notnull}")
        @FutureOrPresent(message = "{validation.plano5w2h.whenFim.future}")
        LocalDate whenFim,

        @Schema(description = "Como será executado", example = SwaggerRequestExamples.HOW_MODO_EXECUCAO)
        @NotBlank(message = "{validation.plano5w2h.howModoExecucao.notblank}")
        @Size(max = 1000, message = "{validation.plano5w2h.howModoExecucao.size}")
        String howModoExecucao,

        @Schema(description = "Quanto custará", example = SwaggerRequestExamples.HOW_MUCH_CUSTO)
        @NotNull(message = "{validation.plano5w2h.howMuchCusto.notnull}")
        @PositiveOrZero(message = "{validation.plano5w2h.howMuchCusto.positiveorzero}")
        @Digits(integer = 3, fraction = 2, message = "{validation.plano5w2h.howMuchCusto.digits}")
        BigDecimal howMuchCusto,

        @Schema(description = "ID do responsável", example = SwaggerRequestExamples.ID_RESPONSAVEL)
        @NotNull(message = "{validation.idResponsavel.notnull}")
        @Positive(message = "{validation.idResponsavel.positive}")
        Long idWhoResponsavel
) {
}
