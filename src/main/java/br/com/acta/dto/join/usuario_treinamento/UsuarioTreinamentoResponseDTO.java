package br.com.acta.dto.join.usuario_treinamento;

import br.com.acta.entity.enums.StatusTreinamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;

public record UsuarioTreinamentoResponseDTO(
        Long idUsuario,
        String nomeUsuario,
        StatusTreinamento status,
        Boolean obrigatorio,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime terminadoEm
) {
}
