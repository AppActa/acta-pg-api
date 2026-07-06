package br.com.acta.dto.pdca.meta;

import br.com.acta.entity.enums.Prioridade;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record MetaRequestDTO(
        @NotBlank(message = "{validation.objetivo.notblank}")
        @Size(max = 1000, message = "{validation.objetivo.size}")
        String objetivo,

        @PositiveOrZero(message = "{validation.meta.valorBase.positiveorzero}")
        @Digits(integer = 13, fraction = 2, message = "{validation.meta.valorBase.digits}")
        BigDecimal valorBase,

        @Positive(message = "{validation.meta.valorAlvo.positive}")
        @Digits(integer = 13, fraction = 2, message = "{validation.meta.valorAlvo.digits}")
        BigDecimal valorAlvo,

        @Size(max = 30, message = "{validation.meta.unidadeMedida.size}")
        String unidadeMedida,

        @NotNull(message = "{validation.meta.prazo.notnull}")
        @Future(message = "{validation.meta.prazo.future}")
        LocalDate prazo,

        @NotNull(message = "{validation.prioridade.notnull}")
        Prioridade prioridade,

        @Size(max = 100, message = "{validation.area.size}")
        String area,

        @Size(max = 100, message = "{validation.meta.categoria.size}")
        String categoria,

        @NotEmpty(message = "{validation.meta.responsaveis.notempty}")
        List<
          @NotNull(message = "{validation.meta.responsaveis.notnull}")
          @Positive(message = "{validation.meta.responsaveis.positive}")
          Long> responsaveis
) {
}
