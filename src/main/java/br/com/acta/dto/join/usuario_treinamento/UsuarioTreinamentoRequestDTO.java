package br.com.acta.dto.join.usuario_treinamento;

import br.com.acta.common.config.swagger.SwaggerExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UsuarioTreinamentoRequestDTO(
        @Schema(description = "ID do colaborador", example = SwaggerExamples.ID_USUARIO)
        @NotNull(message = "{validation.idUsuario.notnull}")
        @Positive(message = "{validation.idUsuario.positive}")
        Long idUsuario,

        @Schema(description = "Indica se o colaborador é obrigatório no treinamento", example = SwaggerExamples.PRINCIPAL)
        @NotNull(message = "{validation.obrigatorio.notnull}")
        Boolean obrigatorio
) {
}