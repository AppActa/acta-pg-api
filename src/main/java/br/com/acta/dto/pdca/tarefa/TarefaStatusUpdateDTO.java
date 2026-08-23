package br.com.acta.dto.pdca.tarefa;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import br.com.acta.entity.enums.StatusTarefa;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TarefaStatusUpdateDTO(
        @Schema(description = "Novo status da tarefa", example = SwaggerRequestExamples.STATUS_TAREFA)
        @NotNull(message = "{validation.status.notnull}")
        StatusTarefa status,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "Data de início real da tarefa, preenchida ao mover para EM_ANDAMENTO", example = SwaggerRequestExamples.DATA_INICIO_REAL, pattern = "yyyy-MM-dd")
        LocalDate dataInicioReal,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "Data de fim real da tarefa, obrigatória ao mover para concluída", example = SwaggerRequestExamples.DATA_FIM_REAL, pattern = "yyyy-MM-dd")
        LocalDate dataFimReal
) {
    @AssertTrue(message = "{validation.tarefa.dataInicioReal.required}")
    @JsonIgnore
    public boolean isDataInicioValida() {
        return status != StatusTarefa.EM_ANDAMENTO || dataInicioReal != null;
    }

    @AssertTrue(message = "{validation.tarefa.dataFimReal.required}")
    @JsonIgnore
    public boolean isDataFimValida() {
        return status != StatusTarefa.CONCLUIDA || dataFimReal != null;
    }
}
