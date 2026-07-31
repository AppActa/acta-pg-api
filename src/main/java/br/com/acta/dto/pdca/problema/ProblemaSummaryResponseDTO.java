package br.com.acta.dto.pdca.problema;

import br.com.acta.entity.enums.OrigemRegistro;
import br.com.acta.entity.enums.StatusProblema;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record ProblemaSummaryResponseDTO(
        Long id,
        String titulo,
        BigDecimal peso,
        StatusProblema status,
        OrigemRegistro origem,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm
) {
}
