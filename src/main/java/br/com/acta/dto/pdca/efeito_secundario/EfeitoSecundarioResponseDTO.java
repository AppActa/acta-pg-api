package br.com.acta.dto.pdca.efeito_secundario;

import br.com.acta.entity.enums.TipoEfeitoSecundario;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record EfeitoSecundarioResponseDTO(
        Long id,
        String descricao,
        BigDecimal peso,
        String impactoEstimado,
        Long idVerificacaoResultado,
        TipoEfeitoSecundario tipo,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
