package br.com.acta.dto.pdca.problema;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import br.com.acta.entity.enums.OrigemRegistro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProblemaRequestDTO(
        @Schema(description = "Título do problema", example = SwaggerRequestExamples.TITULO_PROBLEMA)
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 160, message = "{validation.titulo.size}")
        String titulo,

        @Schema(description = "Descrição do problema", example = SwaggerRequestExamples.DESCRICAO_PROBLEMA)
        @NotBlank(message = "{validation.problema.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @Schema(description = "Peso do problema", example = SwaggerRequestExamples.PESO)
        @NotNull(message = "{validation.peso.notnull}")
        @Positive(message = "{validation.peso.positive}")
        @Digits(integer = 3, fraction = 2, message = "{validation.peso.digits}")
        BigDecimal peso,

        @Schema(description = "Origem do problema", implementation = OrigemRegistro.class, example = SwaggerRequestExamples.ORIGEM)
        @NotNull(message = "{validation.origem.notnull}")
        OrigemRegistro origem,

        @Schema(description = "Persistente", example = SwaggerRequestExamples.PERSISTENTE)
        @NotNull(message = "{validation.problema.persistente.notnull}")
        Boolean persistente,

        @Schema(description = "ID do problema pai", example = SwaggerRequestExamples.ID_PROBLEMA_PAI)
        @Positive(message = "{validation.problema.idProblemaPai.positive}")
        Long idProblemaPai
) {
}
