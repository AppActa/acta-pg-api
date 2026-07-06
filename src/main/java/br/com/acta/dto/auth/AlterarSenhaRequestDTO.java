package br.com.acta.dto.auth;

import br.com.acta.config.swagger.SwaggerExamples;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlterarSenhaRequestDTO(
        @Schema(description = "E-mail do usuário", example = SwaggerExamples.EMAIL, maxLength = 254)
        @Email(message = "{validation.email.invalid}")
        @NotBlank(message = "{validation.email.notblank}")
        @Size(max = 254, message = "{validation.email.size}")
        String email,

        @Schema(description = "Senha atual", example = SwaggerExamples.SENHA, format = "password")
        @NotBlank(message = "{validation.senhaAtual.notblank}")
        String senhaAtual,

        @Schema(description = "Nova senha", example = SwaggerExamples.NOVA_SENHA, format = "password", minLength = 8, maxLength = 254)
        @NotBlank(message = "{validation.novaSenha.notblank}")
        @Size(min = 8, max = 254, message = "{validation.senha.size}")
        String novaSenha
) {
}
