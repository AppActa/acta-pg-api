package br.com.acta.dto.core.colaborador;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta resumida do colaborador")
public record ColaboradorSummaryResponseDTO(
        Long id,
        String nome,
        String cargo
) {
}
