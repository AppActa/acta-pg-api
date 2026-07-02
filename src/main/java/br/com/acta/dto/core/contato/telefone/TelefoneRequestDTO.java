package br.com.acta.dto.core.contato.telefone;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record TelefoneRequestDTO(
        @Schema(description = "Número do telefone", example = "(11) 99999-9999")
        @NotBlank(message = "{validation.telefone.numero.notblank}")
        @Size(max = 20, message = "{validation.telefone.numero.size}")
        @Pattern(regexp = "^\\(\\d{2}\\) \\d{4,5}-\\d{4}$", message = "{validation.telefone.numero.pattern}")
        String numero,

        @Schema(description = "Indica se o telefone é principal", example = "true")
        @NotNull(message = "{validation.telefone.principal.notnull}")
        Boolean principal
) {
}
