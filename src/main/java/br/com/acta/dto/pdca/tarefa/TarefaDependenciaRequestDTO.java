package br.com.acta.dto.pdca.tarefa;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TarefaDependenciaRequestDTO(
        @NotNull(message = "{validation.tarefa.idTarefaDependencia.notnull}")
        @Positive(message = "{validation.tarefa.idTarefaDependencia.positive}")
        Long idTarefaDependencia
) {
}
