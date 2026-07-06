package br.com.acta.dto.pdca.causa_raiz;

import br.com.acta.config.swagger.SwaggerExamples;
import br.com.acta.entity.enums.OrigemRegistro;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CausaRaizRequestDTO(
        @Schema(description = "Descrição da causa raiz", example = SwaggerExamples.DESCRICAO_CAUSA_RAIZ)
        @NotBlank(message = "{validation.causaRaiz.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @Schema(description = "Origem do registro", example = SwaggerExamples.ORIGEM)
        @NotNull(message = "{validation.origem.notnull}")
        OrigemRegistro origem,

        @Schema(description = "Indica se a causa raiz é principal", example = SwaggerExamples.PRINCIPAL)
        @NotNull(message = "{validation.causaRaiz.principal.notnull}")
        Boolean principal,

        @Schema(description = "ID do problema", example = SwaggerExamples.ID_PROBLEMA)
        @NotNull(message = "{validation.idProblema.notnull}")
        @Positive(message = "{validation.idProblema.positive}")
        Long idProblema
) {
}
