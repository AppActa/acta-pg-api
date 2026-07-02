package br.com.acta.dto.join.priorizacao_problema;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PriorizacaoProblemaRequestDTO(
        @NotNull(message = "{validation.priorizacao.idProblema.notnull}")
        @Positive(message = "{validation.priorizacao.idProblema.positive}")
        Long idProblema,

        @NotNull(message = "{validation.priorizacao.posicao.notnull}")
        @Positive(message = "{validation.priorizacao.posicao.positive}")
        Integer posicao,

        @NotNull(message = "{validation.priorizacao.pesoCalculado.notnull}")
        @Positive(message = "{validation.priorizacao.pesoCalculado.positiveOrZero}")
        @Digits(integer = 3, fraction = 2, message = "{validation.priorizacao.pesoCalculado.digits}")
        BigDecimal pesoCalculado
) {
}
