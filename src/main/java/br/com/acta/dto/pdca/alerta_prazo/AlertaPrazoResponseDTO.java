package br.com.acta.dto.pdca.alerta_prazo;

import java.time.OffsetDateTime;

public record AlertaPrazoResponseDTO(
        Long id,
        String mensagem,
        OffsetDateTime enviadoEm,
        OffsetDateTime lidoEm,
        Long idTarefa,
        String tituloTarefa,
        Long idUsuarioDestino
) {
}
