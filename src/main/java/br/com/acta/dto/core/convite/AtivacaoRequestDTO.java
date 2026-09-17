package br.com.acta.dto.core.convite;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtivacaoRequestDTO(
        @Schema(description = "Token recebido no link de convite", example = SwaggerRequestExamples.TOKEN_CONVITE, minLength = 32, maxLength = 512)
        @NotBlank(message = "{validation.convite.token.notblank}")
        @Size(min = 32, max = 512, message = "{validation.convite.token.size}")
        String tokenConvite
) {
}
