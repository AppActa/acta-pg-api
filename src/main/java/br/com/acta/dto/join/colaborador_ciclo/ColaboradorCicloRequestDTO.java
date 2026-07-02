package br.com.acta.dto.join.colaborador_ciclo;

import br.com.acta.entity.enums.PapelCiclo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// TODO talvez substituir esse DTO por um Map<Long, PapelCiclo> no controller,
//  para não precisar criar uma classe só pra isso
public record ColaboradorCicloRequestDTO(
        @Schema(description = "ID do colaborador", example = "1")
        @NotNull(message = "{validation.idColaborador.notnull}")
        @Positive(message = "{validation.idColaborador.positive}")
        Long idColaborador,

        @Schema(description = "Papel do colaborador no ciclo", example = "MEMBRO", implementation = PapelCiclo.class)
        @NotNull(message = "{validation.colaboradorCiclo.papelCiclo.notnull}")
        PapelCiclo papelCiclo
) {
}
