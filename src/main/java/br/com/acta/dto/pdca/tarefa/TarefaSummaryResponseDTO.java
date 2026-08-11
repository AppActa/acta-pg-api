package br.com.acta.dto.pdca.tarefa;

import br.com.acta.entity.enums.Prioridade;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record TarefaSummaryResponseDTO(
        Long id,
        String titulo,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataFimPrevista,
        Prioridade prioridade,
        Long idResponsavel,
        String nomeResponsavel
) {
}
