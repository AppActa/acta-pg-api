package br.com.acta.dto.auth;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
        @Schema(description = "E-mail do usuário", example = SwaggerRequestExamples.EMAIL, maxLength = 254)
        @Email(message = "{validation.email.invalid}")
        @NotBlank(message = "{validation.email.notblank}")
        @Size(max = 254, message = "{validation.email.size}")
        String email,

        @Schema(description = "Senha do usuário", example = SwaggerRequestExamples.SENHA, minLength = 8, maxLength = 254, format = "password")
        @NotBlank(message = "{validation.senha.notblank}")
        @Size(min = 8, max = 254, message = "{validation.senha.size}")
        String senha
) {
}
