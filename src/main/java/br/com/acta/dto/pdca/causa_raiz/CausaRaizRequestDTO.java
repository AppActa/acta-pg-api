package br.com.acta.dto.pdca.causa_raiz;

import br.com.acta.entity.enums.OrigemRegistro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CausaRaizRequestDTO(
        @NotBlank(message = "{validation.causaRaiz.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @NotNull(message = "{validation.origem.notnull}")
        OrigemRegistro origem,

        @NotNull(message = "{validation.causaRaiz.principal.notnull}")
        Boolean principal,

        @NotNull(message = "{validation.causaRaiz.aceita.notnull}")
        Boolean aceita
) {
}
