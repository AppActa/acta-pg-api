package br.com.acta.dto.pdca.problema;

import br.com.acta.common.config.swagger.SwaggerExamples;
import br.com.acta.entity.enums.OrigemRegistro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProblemaRequestDTO(
        @Schema(description = "Título do problema", example = SwaggerExamples.TITULO_PROBLEMA)
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 160, message = "{validation.titulo.size}")
        String titulo,

        @Schema(description = "Descrição do problema", example = SwaggerExamples.DESCRICAO_PROBLEMA)
        @NotBlank(message = "{validation.problema.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @Schema(description = "Peso do problema", example = SwaggerExamples.PESO)
        @NotNull(message = "{validation.peso.notnull}")
        @Positive(message = "{validation.peso.positive}")
        @Digits(integer = 3, fraction = 2, message = "{validation.peso.digits}")
        BigDecimal peso,

        @Schema(description = "Origem do problema", implementation = OrigemRegistro.class, example = SwaggerExamples.ORIGEM)
        @NotNull(message = "{validation.origem.notnull}")
        OrigemRegistro origem,

        @Schema(description = "Persistente", example = SwaggerExamples.PERSISTENTE)
        @NotNull(message = "{validation.problema.persistente.notnull}")
        Boolean persistente,

        @Schema(description = "ID do problema pai", example = SwaggerExamples.ID_PROBLEMA_PAI)
        @Positive(message = "{validation.problema.idProblemaPai.positive}")
        Long idProblemaPai
) {
}
