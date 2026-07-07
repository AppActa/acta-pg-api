package br.com.acta.dto.pdca.problema;

import br.com.acta.entity.enums.OrigemRegistro;
import br.com.acta.entity.enums.StatusProblema;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record ProblemaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        BigDecimal peso,
        StatusProblema status,
        OrigemRegistro origem,
        Boolean persistente,
        Long idProblemaPai,
        Long idCiclo,
        Long idCriadoPor,
        List<ProblemaSummaryResponseDTO> subProblemas,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
