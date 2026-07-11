package br.com.acta.dto.core.contato.telefone;

import br.com.acta.config.swagger.SwaggerExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TelefoneRequestDTO(
        @Schema(description = "Número do telefone", example = SwaggerExamples.TELEFONE)
        @NotBlank(message = "{validation.telefone.numero.notblank}")
        @Size(min = 9, max = 11, message = "{validation.telefone.numero.size}")
        @Pattern(regexp = "^\\d{9,11}$", message = "{validation.telefone.numero.pattern}")
        String numero,

        @Schema(description = "Indica se o telefone é principal", example = SwaggerExamples.PRINCIPAL)
        @NotNull(message = "{validation.telefone.principal.notnull}")
        Boolean principal
) {
}
