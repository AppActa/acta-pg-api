package br.com.acta.dto.core.convite;

import com.fasterxml.jackson.annotation.JsonFormat;
import br.com.acta.entity.enums.StatusConvite;

import java.time.OffsetDateTime;

public record ConviteResponseDTO(
        Long id,
        Long idUsuarioDestino,
        String emailDestino,
        StatusConvite status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime expiraEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime usadoEm,
        Long idCriadoPor,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm
) {
}