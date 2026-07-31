package br.com.acta.dto.pdca.meta;

import br.com.acta.dto.core.usuario.UsuarioSummaryResponseDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusMeta;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public record MetaResponseDTO(
        Long id,
        String objetivo,
        BigDecimal valorBase,
        BigDecimal valorAlvo,
        String unidadeMedida,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate prazo,

        StatusMeta status,
        Prioridade prioridade,
        String area,
        String categoria,
        Long idCiclo,
        List<UsuarioSummaryResponseDTO> responsaveis,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime criadoEm,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        OffsetDateTime atualizadoEm
) {
}
