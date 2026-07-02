package br.com.acta.dto.pdca.plano_5w2h;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record Plano5W2HResponseDTO(
        Long id,
        String whatAcao,
        String whyJustificativa,
        String whereLocal,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate whenInicio,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate whenFim,

        String howModoExecucao,
        BigDecimal howMuchCusto,
        Long idWhoResponsavel,
        Long idPlanoAcao,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
