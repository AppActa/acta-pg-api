package br.com.acta.dto.join.priorizacao_problema;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record PriorizacaoProblemaResponseDTO(
        Long idProblema,
        String tituloProblema,
        Long idUsuario,
        String nomeUsuario,
        Integer posicao,
        BigDecimal pesoCalculado,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
