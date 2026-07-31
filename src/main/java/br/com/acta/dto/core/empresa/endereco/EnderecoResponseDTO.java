package br.com.acta.dto.core.empresa.endereco;

import br.com.acta.entity.enums.UF;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.OffsetDateTime;

public record EnderecoResponseDTO(
        Long id,
        String cep,
        UF uf,
        String cidade,
        String bairro,
        String logradouro,
        String numero,
        String complemento,
        Boolean principal,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
