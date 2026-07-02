package br.com.acta.dto.pdca.ciclo;

import br.com.acta.dto.join.colaborador_ciclo.ColaboradorCicloResponseDTO;
import br.com.acta.entity.enums.StatusCiclo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public record CicloResponseDTO(
        Long id,
        String titulo,
        String descricao,
        StatusCiclo status,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataInicio,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataEstimadaFim,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataFimReal,

        Long idEmpresa,
        Long idGestor,
        List<ColaboradorCicloResponseDTO> colaboradores,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
