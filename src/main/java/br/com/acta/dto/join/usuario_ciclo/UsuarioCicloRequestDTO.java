package br.com.acta.dto.join.usuario_ciclo;

import br.com.acta.config.swagger.SwaggerExamples;
import br.com.acta.entity.enums.PapelCiclo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UsuarioCicloRequestDTO(
        @Schema(description = "ID do usuario", example = SwaggerExamples.ID_USUARIO)
        @NotNull(message = "{validation.idUsuario.notnull}")
        @Positive(message = "{validation.idUsuario.positive}")
        Long idUsuario,

        @Schema(description = "Papel do colaborador no ciclo", example = SwaggerExamples.PAPEL_CICLO, implementation = PapelCiclo.class)
        @NotNull(message = "{validation.usuarioCiclo.papelCiclo.notnull}")
        PapelCiclo papelCiclo
) {
}
