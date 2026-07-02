package br.com.acta.dto.pdca.ciclo;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CicloRequestDTO(
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 160, message = "{validation.titulo.size}")
        String titulo,

        @NotBlank(message = "{validation.ciclo.descricao.notblank}")
        @Size(max = 1000, message = "{validation.ciclo.descricao.size}")
        String descricao,

        @NotNull(message = "{validation.ciclo.dataInicio.notNull}")
        @FutureOrPresent(message = "{validation.ciclo.dataInicio.futureOrPresent}")
        LocalDate dataInicio,

        @NotNull(message = "{validation.ciclo.dataEstimadaFim.notNull}")
        @FutureOrPresent(message = "{validation.ciclo.dataEstimadaFim.future}")
        LocalDate dataEstimadaFim,

        @NotNull(message = "{validation.idEmpresa.notnull}")
        @Positive(message = "{validation.idEmpresa.positive}")
        Long idEmpresa,

        @NotNull(message = "{validation.ciclo.idGestor.notnull}")
        @Positive(message = "{validation.ciclo.idGestor.positive}")
        Long idGestor
) {
}
