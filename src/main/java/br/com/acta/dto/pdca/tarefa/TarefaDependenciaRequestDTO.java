package br.com.acta.dto.pdca.tarefa;

import br.com.acta.config.swagger.SwaggerExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TarefaDependenciaRequestDTO(
        @Schema(description = "ID da tarefa dependência", example = SwaggerExamples.ID_TAREFA_DEPENDENCIA)
        @NotNull(message = "{validation.tarefa.idTarefaDependencia.notnull}")
        @Positive(message = "{validation.tarefa.idTarefaDependencia.positive}")
        Long idTarefaDependencia
) {
}
