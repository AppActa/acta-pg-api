package br.com.acta.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AlterarSenhaRequestDTO(
        @Schema(description = "E-mail do usuário", example = "usuario@email.com", maxLength = 254)
        @Email(message = "{validation.email.invalid}")
        @NotBlank(message = "{validation.email.notblank}")
        @Size(max = 254, message = "{validation.email.size}")
        String email,

        @Schema(description = "Senha atual", example = "MinhaSenhaSegura@123", format = "password")
        @NotBlank(message = "{validation.senhaAtual.notblank}")
        String senhaAtual,

        @Schema(description = "Nova senha", example = "MinhaNovaSenha!123", format = "password")
        @NotBlank(message = "{validation.novaSenha.notblank}")
        @Size(min = 8, max = 254, message = "{validation.senha.size}")
        String novaSenha
) {
}
