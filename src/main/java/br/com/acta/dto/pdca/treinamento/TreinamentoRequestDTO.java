package br.com.acta.dto.pdca.treinamento;

import br.com.acta.config.swagger.SwaggerExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TreinamentoRequestDTO(
        @Schema(description = "Título do treinamento", example = SwaggerExamples.TITULO_TREINAMENTO)
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 160, message = "{validation.titulo.size}")
        String titulo,

        @Schema(description = "Descrição do treinamento", example = SwaggerExamples.DESCRICAO_TREINAMENTO)
        @NotBlank(message = "{validation.treinamento.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @Schema(description = "Data do treinamento", example = SwaggerExamples.DATA_TREINAMENTO)
        @NotNull(message = "{validation.treinamento.dataTreinamento.notnull}")
        @FutureOrPresent(message = "{validation.treinamento.dataTreinamento.futureorpresent}")
        LocalDate dataTreinamento,

        @Schema(description = "Obrigatório", example = SwaggerExamples.OBRIGATORIO)
        @NotNull(message = "{validation.obrigatorio.notnull}")
        Boolean obrigatorio,

        @Schema(description = "ID do responsável pelo treinamento", example = SwaggerExamples.ID_RESPONSAVEL)
        @NotNull(message = "{validation.idResponsavel.notnull}")
        @Positive(message = "{validation.idResponsavel.positive}")
        Long idResponsavel,

        @Schema(description = "ID do anexo no MongoDB", example = SwaggerExamples.ID_ANEXO_MONGO)
        @Positive(message = "{validation.treinamento.idAnexoMongo.positive}")
        Integer idAnexoMongo
) {
}
