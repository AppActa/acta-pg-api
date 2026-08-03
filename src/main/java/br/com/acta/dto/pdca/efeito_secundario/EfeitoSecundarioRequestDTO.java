package br.com.acta.dto.pdca.efeito_secundario;

import br.com.acta.common.config.swagger.SwaggerExamples;
import br.com.acta.entity.enums.TipoEfeitoSecundario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record EfeitoSecundarioRequestDTO(
        @Schema(description = "Descrição do efeito secundário", example = SwaggerExamples.DESCRICAO_EFEITO_SECUNDARIO)
        @NotBlank(message = "{validation.efeitoSecundario.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @Schema(description = "Peso do efeito secundário", example = SwaggerExamples.PESO)
        @NotNull(message = "{validation.peso.notnull}")
        @Positive(message = "{validation.peso.positive}")
        @Digits(integer = 3, fraction = 2, message = "{validation.peso.digits}")
        BigDecimal peso,

        @Schema(description = "Impacto estimado do efeito secundário", example = SwaggerExamples.IMPACTO_ESTIMADO)
        @Size(max = 1000, message = "{validation.efeitoSecundario.impactoEstimado.size}")
        String impactoEstimado,

        @Schema(description = "Tipo do efeito secundário", implementation = TipoEfeitoSecundario.class, example = SwaggerExamples.TIPO_EFEITO_SECUNDARIO)
        @NotNull(message = "{validation.efeitoSecundario.tipo.notnull}")
        TipoEfeitoSecundario tipo
) {
}
