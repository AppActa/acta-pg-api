package br.com.acta.dto.join.colaborador_treinamento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// TODO talvez substituir esse DTO por um Map<Long, Boolean> no controller,
//  para não precisar criar uma classe só pra isso
public record ColaboradorTreinamentoRequestDTO(
        @Schema(description = "ID do colaborador", example = "1")
        @NotNull(message = "{validation.idColaborador.notnull}")
        @Positive(message = "{validation.idColaborador.positive}")
        Long idColaborador,

        @Schema(description = "Indica se o colaborador é obrigatório no treinamento", example = "true")
        @NotNull(message = "{validation.obrigatorio.notnull}")
        Boolean obrigatorio
) {
}
