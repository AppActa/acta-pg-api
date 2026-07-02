package br.com.acta.dto.pdca.tarefa;

import br.com.acta.entity.enums.Prioridade;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public record TarefaResponseDTO(
        Long id,
        String titulo,
        String descricao,
        Prioridade prioridade,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataInicioReal,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataFimPrevista,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataFimReal,

        Long idPlanoAcao,
        Long idResponsavel,
        String nomeResponsavel,
        List<TarefaSummaryResponseDTO> dependencias,
        List<TarefaSummaryResponseDTO> dependentes,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
