package br.com.acta.dto.core.usuario;

import br.com.acta.entity.enums.TipoUsuario;

public record UsuarioSummaryResponseDTO(
        Long id,
        String nome,
        TipoUsuario tipo
) {
}
