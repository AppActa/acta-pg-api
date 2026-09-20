package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizRequestDTO;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Causas-raiz", description = SwaggerOpenapiDescriptions.CAUSA_RAIZ_CONTROLLER)
public interface CausaRaizOpenapi {

    @Operation(summary = "Lista as causas-raiz de um ciclo")
    @ApiResponse(responseCode = "200", description = "Causas-raiz encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CausaRaizResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<CausaRaizResponseDTO>> buscar(Long idCiclo, Boolean aceita, Boolean principal, Long idProblema);

    @Operation(summary = "Busca uma causa-raiz")
    @ApiResponse(responseCode = "200", description = "Causa-raiz encontrada", content = @Content(schema = @Schema(implementation = CausaRaizResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<CausaRaizResponseDTO> buscar(Long id);

    @Operation(summary = "Cria uma causa-raiz")
    @ApiResponse(responseCode = "201", description = "Causa-raiz criada", content = @Content(schema = @Schema(implementation = CausaRaizResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<CausaRaizResponseDTO> inserir(Long idCiclo, CausaRaizRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente uma causa-raiz")
    @ApiResponse(responseCode = "200", description = "Causa-raiz atualizada", content = @Content(schema = @Schema(implementation = CausaRaizResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<CausaRaizResponseDTO> patch(Long id, Map<String, Object> campos);

    @Operation(summary = "Valida uma causa-raiz")
    @ApiResponse(responseCode = "200", description = "Causa-raiz validada", content = @Content(schema = @Schema(implementation = CausaRaizResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<CausaRaizResponseDTO> validar(Long id, Long idUsuario, Boolean aceita);

    @Operation(summary = "Exclui uma causa-raiz")
    @ApiResponse(responseCode = "204", description = "Causa-raiz excluída")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    ResponseEntity<Void> excluir(Long id);
}
