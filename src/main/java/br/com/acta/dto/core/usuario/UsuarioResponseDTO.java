package br.com.acta.dto.core.usuario;

import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TipoUsuario;
import java.time.OffsetDateTime;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TipoUsuario tipo,
        Long idEmpresa,
        StatusGeral status,
        OffsetDateTime criadoEm,
        OffsetDateTime atualizadoEm
) {
}
