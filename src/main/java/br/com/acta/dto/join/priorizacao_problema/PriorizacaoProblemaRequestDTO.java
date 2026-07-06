package br.com.acta.dto.join.priorizacao_problema;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PriorizacaoProblemaRequestDTO(
        @NotNull(message = "{validation.idProblema.notnull}")
        @Positive(message = "{validation.idProblema.positive}")
        Long idProblema,

        @NotNull(message = "{validation.priorizacao.posicao.notnull}")
        @Positive(message = "{validation.priorizacao.posicao.positive}")
        Integer posicao,

        @NotNull(message = "{validation.peso.notnull}")
        @Positive(message = "{validation.peso.positive}")
        @Digits(integer = 3, fraction = 2, message = "{validation.peso.digits}")
        BigDecimal pesoCalculado
) {
}
