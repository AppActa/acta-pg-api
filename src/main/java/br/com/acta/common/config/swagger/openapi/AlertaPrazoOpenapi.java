package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.pdca.alerta_prazo.AlertaPrazoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Alertas de prazo", description = SwaggerOpenapiDescriptions.ALERTA_PRAZO_CONTROLLER)
public interface AlertaPrazoOpenapi {

    @Operation(summary = "Busca o alerta de prazo de uma tarefa")
    @ApiResponse(responseCode = "200", description = "Alerta encontrado", content = @Content(schema = @Schema(implementation = AlertaPrazoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<AlertaPrazoResponseDTO> buscar(Long idTarefa);

    @Operation(summary = "Marca um alerta como lido")
    @ApiResponse(responseCode = "200", description = "Alerta marcado como lido", content = @Content(schema = @Schema(implementation = AlertaPrazoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<AlertaPrazoResponseDTO> marcarLido(Long idTarefa, Long id, Long idUsuario);
}
