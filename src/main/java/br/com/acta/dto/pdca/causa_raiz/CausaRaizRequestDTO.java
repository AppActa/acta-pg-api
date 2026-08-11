package br.com.acta.dto.pdca.causa_raiz;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import br.com.acta.entity.enums.OrigemRegistro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CausaRaizRequestDTO(
        @Schema(description = "Descrição da causa raiz", example = SwaggerRequestExamples.DESCRICAO_CAUSA_RAIZ)
        @NotBlank(message = "{validation.causaRaiz.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @Schema(description = "Origem do registro", example = SwaggerRequestExamples.ORIGEM)
        @NotNull(message = "{validation.origem.notnull}")
        OrigemRegistro origem,

        @Schema(description = "Indica se a causa raiz é principal", example = SwaggerRequestExamples.PRINCIPAL)
        @NotNull(message = "{validation.causaRaiz.principal.notnull}")
        Boolean principal,

        @Schema(description = "ID do problema", example = SwaggerRequestExamples.ID_PROBLEMA)
        @NotNull(message = "{validation.idProblema.notnull}")
        @Positive(message = "{validation.idProblema.positive}")
        Long idProblema,

        @Schema(description = "ID do registro correspondente na ferramenta dos 5 porquês (mongo), quando aplicável", example = SwaggerRequestExamples.ID_5_PORQUES)
        String id5PorquesMongo
) {
}
