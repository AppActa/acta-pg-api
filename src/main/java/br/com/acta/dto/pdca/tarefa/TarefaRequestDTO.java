package br.com.acta.dto.pdca.tarefa;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import br.com.acta.entity.enums.Prioridade;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TarefaRequestDTO(
        @Schema(description = "Título da tarefa", example = SwaggerRequestExamples.TITULO_TAREFA)
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 160, message = "{validation.titulo.size}")
        String titulo,

        @Schema(description = "Descrição da tarefa", example = SwaggerRequestExamples.DESCRICAO_TAREFA)
        @NotBlank(message = "{validation.tarefa.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @Schema(description = "Prioridade da tarefa", example = SwaggerRequestExamples.PRIORIDADE)
        @NotNull(message = "{validation.prioridade.notnull}")
        Prioridade prioridade,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "Data de término prevista", example = SwaggerRequestExamples.DATA_FIM_PREVISTA, pattern = "yyyy-MM-dd")
        @NotNull(message = "{validation.tarefa.dataFimPrevista.notnull}")
        @Future(message = "{validation.tarefa.dataFimPrevista.future}")
        LocalDate dataFimPrevista,

        @Schema(description = "ID do responsável pela tarefa", example = SwaggerRequestExamples.ID_RESPONSAVEL)
        @NotNull(message = "{validation.idResponsavel.notnull}")
        @Positive(message = "{validation.idResponsavel.positive}")
        Long idResponsavel
) {
}
