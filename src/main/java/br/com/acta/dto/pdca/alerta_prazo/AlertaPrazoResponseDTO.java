package br.com.acta.dto.pdca.alerta_prazo;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Resposta para alerta de prazo")
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
