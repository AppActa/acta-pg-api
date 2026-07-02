package br.com.acta.dto.pdca.efeito_secundario;

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

        @NotBlank(message = "{validation.efeitoSecundario.impactoEstimado.notblank}")
        @Size(max = 1000, message = "{validation.efeitoSecundario.impactoEstimado.size}")
        String impactoEstimado
) {
}
