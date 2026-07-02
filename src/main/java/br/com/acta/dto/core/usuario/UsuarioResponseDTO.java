package br.com.acta.dto.core.usuario;

import br.com.acta.entity.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Resposta para informações do usuário")
public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TipoUsuario tipo,
        Long idEmpresa,
        OffsetDateTime criadoEm,
        OffsetDateTime atualizadoEm
) {
}
