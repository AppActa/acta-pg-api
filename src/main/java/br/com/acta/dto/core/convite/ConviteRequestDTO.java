package br.com.acta.dto.core.convite;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ConviteRequestDTO(
        @Schema(description = "ID do usuário destinatário do convite", example = SwaggerRequestExamples.ID_USUARIO)
        @NotNull(message = "{validation.idUsuario.notnull}")
        @Positive(message = "{validation.idUsuario.positive}")
        Long idUsuarioDestino,

        @Schema(description = "E-mail que receberá o convite", example = SwaggerRequestExamples.EMAIL, maxLength = 254)
        @NotBlank(message = "{validation.usuario.emailLogin.notblank}")
        @Email(message = "{validation.usuario.emailLogin.invalid}")
        String emailDestino
) {
}
