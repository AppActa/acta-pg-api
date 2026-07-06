package br.com.acta.dto.pdca.causa_raiz;

import br.com.acta.entity.enums.OrigemRegistro;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;

public record CausaRaizResponseDTO(
        Long id,
        String descricao,
        OrigemRegistro origem,
        Boolean aceita,
        Boolean principal,
        OffsetDateTime validadaEm,
        Long idCiclo,
        Long idValidadaPor,
        Long idProblema,
        Long idPlanoAcao,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
