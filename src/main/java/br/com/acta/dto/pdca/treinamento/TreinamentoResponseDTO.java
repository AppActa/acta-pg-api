package br.com.acta.dto.pdca.treinamento;

import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoResponseDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public record TreinamentoResponseDTO(
        Long id,
        String titulo,
        String descricao,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate dataTreinamento,
        Boolean obrigatorio,
        Long idCiclo,
        Long idResponsavel,
        String nomeResponsavel,
        Integer idAnexoMongo,
        List<UsuarioTreinamentoResponseDTO> participantes,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
