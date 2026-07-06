package br.com.acta.dto.core.contato.telefone;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;

public record TelefoneResponseDTO(
        Long id,
        String numero,
        Boolean principal,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm
) {
}
