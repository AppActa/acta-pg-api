package br.com.acta.dto.pdca.verificacao_resultado;

import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioResponseDTO;
import br.com.acta.entity.enums.StatusVerificacao;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.OffsetDateTime;
import java.util.List;

public record VerificacaoResultadoResponseDTO(
        Long id,
        StatusVerificacao status,
        String resumo,
        String observacao,
        Long idCiclo,
        Long idCriadoPor,
        List<EfeitoSecundarioResponseDTO> efeitosSecundarios,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm
) {
}
