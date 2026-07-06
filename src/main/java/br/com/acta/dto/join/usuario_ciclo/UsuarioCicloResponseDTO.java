package br.com.acta.dto.join.usuario_ciclo;

import br.com.acta.entity.enums.PapelCiclo;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Resposta para a entidade UsuarioCiclo")
public record UsuarioCicloResponseDTO(
        Long idUsuario,
        Long idCiclo,
        String nomeUsuario,
        PapelCiclo papelCiclo,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm
) {
}