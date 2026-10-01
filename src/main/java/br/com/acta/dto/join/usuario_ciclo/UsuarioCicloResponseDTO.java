package br.com.acta.dto.join.usuario_ciclo;

import br.com.acta.entity.enums.PapelCiclo;
import br.com.acta.entity.enums.StatusCiclo;

public record UsuarioCicloResponseDTO(
        Long idUsuario,
        Long idCiclo,
        String nomeUsuario,
        PapelCiclo papelCiclo,
        StatusCiclo statusCiclo,
        String nomeCiclo,
        String iconeUrl
) {
}