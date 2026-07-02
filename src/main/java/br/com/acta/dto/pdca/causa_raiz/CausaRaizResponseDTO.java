package br.com.acta.dto.pdca.causa_raiz;

import br.com.acta.entity.enums.OrigemRegistro;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Resposta para Causa Raiz")
public record CausaRaizResponseDTO(
        Long id,
        String descricao,
        OrigemRegistro origem,
        Boolean aceita,
        Boolean principal,
        OffsetDateTime validadaEm,
        Long idCiclo,
        Long idValidadaPor,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
