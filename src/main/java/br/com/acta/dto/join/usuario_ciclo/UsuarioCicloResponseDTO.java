package br.com.acta.dto.join.usuario_ciclo;

import br.com.acta.entity.enums.PapelCiclo;

public record UsuarioCicloResponseDTO(
        Long idUsuario,
        Long idCiclo,
        String nomeUsuario,
        PapelCiclo papelCiclo
) {
}