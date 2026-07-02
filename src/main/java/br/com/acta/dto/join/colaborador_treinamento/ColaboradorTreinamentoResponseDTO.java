package br.com.acta.dto.join.colaborador_treinamento;

import br.com.acta.entity.enums.StatusTreinamento;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta para a entidade UsuarioTreinamento")
public record ColaboradorTreinamentoResponseDTO(
        Long id,
        Long idColaborador,
        Long idTreinamento,
        String nomeColaborador,
        StatusTreinamento status,
        Boolean obrigatorio
) {
}
