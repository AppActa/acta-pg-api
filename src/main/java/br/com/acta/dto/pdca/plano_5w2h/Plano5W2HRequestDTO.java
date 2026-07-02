package br.com.acta.dto.pdca.plano_5w2h;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Plano5W2HRequestDTO(
        @NotBlank(message = "{validation.plano5w2h.whatAcao.notblank}")
        @Size(max = 1000, message = "{validation.plano5w2h.whatAcao.size}")
        String whatAcao,

        @NotBlank(message = "{validation.plano5w2h.whyJustificativa.notblank}")
        @Size(max = 1000, message = "{validation.plano5w2h.whyJustificativa.size}")
        String whyJustificativa,

        @NotBlank(message = "{validation.plano5w2h.whereLocal.notblank}")
        @Size(max = 1000, message = "{validation.plano5w2h.whereLocal.size}")
        String whereLocal,

        @FutureOrPresent(message = "{validation.plano5w2h.whenInicio.futureOrPresent}")
        LocalDate whenInicio,

        @NotNull(message = "{validation.plano5w2h.whenFim.notNull}")
        @FutureOrPresent(message = "{validation.plano5w2h.whenFim.future}")
        LocalDate whenFim,

        @NotBlank(message = "{validation.plano5w2h.howModoExecucao.notblank}")
        @Size(max = 1000, message = "{validation.plano5w2h.howModoExecucao.size}")
        String howModoExecucao,

        @NotNull(message = "{validation.plano5w2h.howMuchCusto.notnull}")
        @PositiveOrZero(message = "{validation.plano5w2h.howMuchCusto.positiveOrZero}")
        @Digits(integer = 3, fraction = 2, message = "{validation.plano5w2h.howMuchCusto.digits}")
        BigDecimal howMuchCusto,

        @NotNull(message = "{validation.idResponsavel.notnull}")
        @Positive(message = "{validation.idResponsavel.positive}")
        Long idWhoResponsavel
) {
}
