package br.com.acta.dto.pdca.plano_acao;

import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HResponseDTO;
import br.com.acta.dto.pdca.tarefa.TarefaSummaryResponseDTO;
import br.com.acta.entity.enums.OrigemRegistro;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.List;

public record PlanoAcaoResponseDTO(
        Long id,
        String nome,
        String objetivo,
        Prioridade prioridade,
        StatusPlanoAcao status,
        OrigemRegistro origem,
        Long idCiclo,
        Long idCausaRaiz,
        Plano5W2HResponseDTO plano5W2H,
        List<TarefaSummaryResponseDTO> tarefas,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
