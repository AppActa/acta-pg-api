package br.com.acta.dto.pdca.treinamento;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record TreinamentoRequestDTO(
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 160, message = "{validation.titulo.size}")
        String titulo,

        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @NotNull(message = "{validation.treinamento.dataTreinamento.notNull}")
        @FutureOrPresent(message = "{validation.treinamento.dataTreinamento.futureOrPresent}")
        LocalDate dataTreinamento,

        @NotNull(message = "{validation.obrigatorio.notnull}")
        Boolean obrigatorio,

        @NotNull(message = "{validation.idResponsavel.notnull}")
        @Positive(message = "{validation.idResponsavel.positive}")
        Long idResponsavel,

        @Positive(message = "{validation.treinamento.idAnexoMongo.positive}")
        Integer idAnexoMongo
) {
}
