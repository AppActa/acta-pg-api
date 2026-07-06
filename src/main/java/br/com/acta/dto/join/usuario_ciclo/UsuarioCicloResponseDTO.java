package br.com.acta.dto.join.usuario_ciclo;

import br.com.acta.entity.enums.PapelCiclo;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;

public record UsuarioCicloResponseDTO(
        Long idUsuario,
        Long idCiclo,
        String nomeUsuario,
        PapelCiclo papelCiclo,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm
) {
}