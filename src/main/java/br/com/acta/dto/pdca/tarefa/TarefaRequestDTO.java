package br.com.acta.dto.pdca.tarefa;

import br.com.acta.config.swagger.SwaggerExamples;
import br.com.acta.entity.enums.Prioridade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TarefaRequestDTO(
        @Schema(description = "Título da tarefa", example = SwaggerExamples.TITULO_TAREFA)
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 160, message = "{validation.titulo.size}")
        String titulo,

        @Schema(description = "Descrição da tarefa", example = SwaggerExamples.DESCRICAO_TAREFA)
        @NotBlank(message = "{validation.tarefa.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @Schema(description = "Prioridade da tarefa", example = SwaggerExamples.PRIORIDADE)
        @NotNull(message = "{validation.prioridade.notnull}")
        Prioridade prioridade,

        @Schema(description = "Data de término prevista", example = SwaggerExamples.DATA_FIM_PREVISTA)
        @NotNull(message = "{validation.tarefa.dataFimPrevista.notnull}")
        @Future(message = "{validation.tarefa.dataFimPrevista.future}")
        LocalDate dataFimPrevista,

        @Schema(description = "ID do responsável pela tarefa", example = SwaggerExamples.ID_RESPONSAVEL)
        @NotNull(message = "{validation.idResponsavel.notnull}")
        @Positive(message = "{validation.idResponsavel.positive}")
        Long idResponsavel
) {
}
