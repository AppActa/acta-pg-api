package br.com.acta.dto.core.contato.email;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public record EmailResponseDTO(
        Long id,
        String email,
        Boolean principal,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm
) {
}
