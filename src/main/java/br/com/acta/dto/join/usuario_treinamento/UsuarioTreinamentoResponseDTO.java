package br.com.acta.dto.join.usuario_treinamento;

import br.com.acta.entity.enums.StatusTreinamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Resposta para a entidade UsuarioTreinamento")
public record UsuarioTreinamentoResponseDTO(
        Long idUsuario,
        Long idTreinamento,
        String nomeUsuario,
        StatusTreinamento status,
        Boolean obrigatorio,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime terminadoEm
) {
}
