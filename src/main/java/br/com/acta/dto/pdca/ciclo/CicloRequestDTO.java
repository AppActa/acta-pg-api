package br.com.acta.dto.pdca.ciclo;

import br.com.acta.config.swagger.SwaggerExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CicloRequestDTO(
        @Schema(description = "Título do ciclo", example = SwaggerExamples.TITULO_CICLO)
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 160, message = "{validation.titulo.size}")
        String titulo,

        @Schema(description = "Descrição do ciclo", example = SwaggerExamples.DESCRICAO_CICLO)
        @NotBlank(message = "{validation.ciclo.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @Schema(description = "Data de início do ciclo", example = SwaggerExamples.DATA_INICIO_CICLO)
        @NotNull(message = "{validation.ciclo.dataInicio.notnull}")
        @FutureOrPresent(message = "{validation.ciclo.dataInicio.futureorpresent}")
        LocalDate dataInicio,

        @Schema(description = "Data estimada de fim do ciclo", example = SwaggerExamples.DATA_ESTIMADA_FIM)
        @NotNull(message = "{validation.ciclo.dataEstimadaFim.notnull}")
        @FutureOrPresent(message = "{validation.ciclo.dataEstimadaFim.future}")
        LocalDate dataEstimadaFim,

        @Schema(description = "ID da empresa", example = SwaggerExamples.ID_EMPRESA)
        @NotNull(message = "{validation.idEmpresa.notnull}")
        @Positive(message = "{validation.idEmpresa.positive}")
        Long idEmpresa,

        @Schema(description = "ID do gestor", example = SwaggerExamples.ID_GESTOR)
        @NotNull(message = "{validation.ciclo.idGestor.notnull}")
        @Positive(message = "{validation.ciclo.idGestor.positive}")
        Long idGestor
) {
}
