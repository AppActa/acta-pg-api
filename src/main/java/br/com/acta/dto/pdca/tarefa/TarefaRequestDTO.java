package br.com.acta.dto.pdca.tarefa;

import br.com.acta.entity.enums.Prioridade;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TarefaRequestDTO(
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 160, message = "{validation.titulo.size}")
        String titulo,

        @NotBlank(message = "{validation.tarefa.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @NotNull(message = "{validation.prioridade.notnull}")
        Prioridade prioridade,

        @NotNull(message = "{validation.tarefa.dataFimPrevista.notnull}")
        @Future(message = "{validation.tarefa.dataFimPrevista.future}")
        LocalDate dataFimPrevista,

        @NotNull(message = "{validation.idResponsavel.notnull}")
        @Positive(message = "{validation.idResponsavel.positive}")
        Long idResponsavel
) {
}
