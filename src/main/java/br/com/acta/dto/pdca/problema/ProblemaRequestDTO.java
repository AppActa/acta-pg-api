package br.com.acta.dto.pdca.problema;

import br.com.acta.entity.enums.OrigemRegistro;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProblemaRequestDTO(
        @NotBlank(message = "{validation.titulo.notblank}")
        @Size(max = 180, message = "{validation.titulo.size}")
        String titulo,

        @NotBlank(message = "{validation.problema.descricao.notblank}")
        @Size(max = 1000, message = "{validation.descricao.size}")
        String descricao,

        @NotNull(message = "{validation.peso.notnull}")
        @Positive(message = "{validation.peso.positive}")
        @Digits(integer = 3, fraction = 2, message = "{validation.peso.digits}")
        BigDecimal peso,

        @NotNull(message = "{validation.origem.notnull}")
        OrigemRegistro origem,

        @NotNull(message = "validation.problema.persistente.notnull")
        Boolean persistente,

        @Positive(message = "validation.problema.idProblemaPai.positive")
        Long idProblemaPai,

        @NotNull(message = "validation.idCausaRaiz.notnull")
        @Positive(message = "validation.idCausaRaiz.positive")
        Long idCausaRaiz
) {
}
