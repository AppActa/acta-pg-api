package br.com.acta.dto.join.usuario_treinamento;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UsuarioTreinamentoRequestDTO(
        @Schema(description = "ID do colaborador", example = "1")
        @NotNull(message = "{validation.idUsuario.notnull}")
        @Positive(message = "{validation.idUsuario.positive}")
        Long idUsuario,

        @Schema(description = "Indica se o colaborador é obrigatório no treinamento", example = "true")
        @NotNull(message = "{validation.obrigatorio.notnull}")
        Boolean obrigatorio
) {
}