package br.com.acta.dto.pdca.efeito_secundario;

import br.com.acta.entity.enums.TipoEfeitoSecundario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record EfeitoSecundarioRequestDTO(
        @NotBlank(message = "{validation.efeitoSecundario.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @NotNull(message = "{validation.peso.notnull}")
        @Positive(message = "{validation.peso.positive}")
        @Digits(integer = 3, fraction = 2, message = "{validation.peso.digits}")
        BigDecimal peso,

        @Size(max = 1000, message = "{validation.efeitoSecundario.impactoEstimado.size}")
        String impactoEstimado,

        @Schema(implementation = TipoEfeitoSecundario.class)
        @NotNull(message = "{validation.efeitoSecundario.tipo.notnull}")
        TipoEfeitoSecundario tipo
) {
}
