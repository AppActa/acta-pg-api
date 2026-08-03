package br.com.acta.dto.pdca.meta;

import br.com.acta.common.config.swagger.SwaggerExamples;
import br.com.acta.entity.enums.Prioridade;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record MetaRequestDTO(
        @Schema(description = "Objetivo da meta", example = SwaggerExamples.OBJETIVO_META)
        @NotBlank(message = "{validation.objetivo.notblank}")
        @Size(max = 1000, message = "{validation.objetivo.size}")
        String objetivo,

        @Schema(description = "Valor base da meta", example = SwaggerExamples.VALOR_BASE)
        @PositiveOrZero(message = "{validation.meta.valorBase.positiveorzero}")
        @Digits(integer = 13, fraction = 2, message = "{validation.meta.valorBase.digits}")
        BigDecimal valorBase,

        @Schema(description = "Valor alvo da meta", example = SwaggerExamples.VALOR_ALVO)
        @Positive(message = "{validation.meta.valorAlvo.positive}")
        @Digits(integer = 13, fraction = 2, message = "{validation.meta.valorAlvo.digits}")
        BigDecimal valorAlvo,

        @Schema(description = "Unidade de medida da meta", example = SwaggerExamples.UNIDADE_MEDIDA)
        @Size(max = 30, message = "{validation.meta.unidadeMedida.size}")
        String unidadeMedida,

        @Schema(description = "Prazo da meta", example = SwaggerExamples.PRAZO_META)
        @NotNull(message = "{validation.meta.prazo.notnull}")
        @Future(message = "{validation.meta.prazo.future}")
        LocalDate prazo,

        @Schema(description = "Prioridade da meta", implementation = Prioridade.class, example = SwaggerExamples.PRIORIDADE)
        @NotNull(message = "{validation.prioridade.notnull}")
        Prioridade prioridade,

        @Schema(description = "Área da meta", example = SwaggerExamples.AREA)
        @Size(max = 100, message = "{validation.area.size}")
        String area,

        @Schema(description = "Categoria da meta", example = SwaggerExamples.CATEGORIA)
        @Size(max = 100, message = "{validation.meta.categoria.size}")
        String categoria,

        @ArraySchema(schema = @Schema(implementation = Long.class, example = SwaggerExamples.ID_RESPONSAVEL), minItems = 1, uniqueItems = true, arraySchema = @Schema(description = "Lista de IDs de responsáveis pela meta"))
        @NotEmpty(message = "{validation.meta.responsaveis.notempty}")
        List<
          @NotNull(message = "{validation.meta.responsaveis.notnull}")
          @Positive(message = "{validation.meta.responsaveis.positive}")
          Long> responsaveis
) {
}
