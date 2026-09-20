package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HRequestDTO;
import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@Tag(name = "Planos 5W2H", description = SwaggerOpenapiDescriptions.PLANO_5W2H_CONTROLLER)
public interface Plano5W2HOpenapi {

    @Operation(summary = "Busca o plano 5W2H de um plano de ação")
    @ApiResponse(responseCode = "200", description = "Plano 5W2H encontrado", content = @Content(schema = @Schema(implementation = Plano5W2HResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<Plano5W2HResponseDTO> buscar(Long idPlanoAcao);

    @Operation(summary = "Cria um plano 5W2H")
    @ApiResponse(responseCode = "201", description = "Plano 5W2H criado", content = @Content(schema = @Schema(implementation = Plano5W2HResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<Plano5W2HResponseDTO> inserir(Long idPlanoAcao, Plano5W2HRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente um plano 5W2H")
    @ApiResponse(responseCode = "200", description = "Plano 5W2H atualizado", content = @Content(schema = @Schema(implementation = Plano5W2HResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<Plano5W2HResponseDTO> patch(Long id, Map<String, Object> campos);

    @Operation(summary = "Exclui um plano 5W2H")
    @ApiResponse(responseCode = "204", description = "Plano 5W2H excluído")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(Long id);
}
