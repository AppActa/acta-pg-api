package br.com.acta.dto.core.usuario;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import br.com.acta.entity.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public record UsuarioRequestDTO(
        @Schema(description = "Nome do usuário", example = SwaggerRequestExamples.NOME)
        @NotBlank(message = "{validation.usuario.nome.notblank}")
        @Size(max = 160, message = "{validation.nome.size}")
        String nome,

        @Schema(description = "Email do usuário", example = SwaggerRequestExamples.EMAIL)
        @NotBlank(message = "{validation.usuario.emailLogin.notblank}")
        @Size(max = 254, message = "{validation.usuario.emailLogin.size}")
        @Email(message = "{validation.usuario.emailLogin.invalid}")
        String email,

        @Schema(description = "Senha do usuário", example = SwaggerRequestExamples.SENHA)
        @NotBlank(message = "{validation.senha.notblank}")
        @Size(min = 8, max = 128, message = "{validation.senha.size}")
        String senha,

        @Schema(description = "Tipo do usuário", example = SwaggerRequestExamples.TIPO_USUARIO, implementation = TipoUsuario.class)
        @NotNull(message = "{validation.usuario.tipo.notnull}")
        TipoUsuario tipo,

        @Schema(description = "ID da empresa associada ao usuário", example = SwaggerRequestExamples.ID_EMPRESA)
        @NotNull(message = "{validation.idEmpresa.notnull}")
        @Positive(message = "{validation.idEmpresa.positive}")
        Long idEmpresa
) {
}
