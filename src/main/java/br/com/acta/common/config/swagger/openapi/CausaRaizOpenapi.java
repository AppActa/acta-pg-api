package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizRequestDTO;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Causas-raiz", description = SwaggerOpenapiDescriptions.CAUSA_RAIZ_CONTROLLER)
public interface CausaRaizOpenapi {

    @Operation(summary = "Lista as causas-raiz de um ciclo")
    @ApiResponse(responseCode = "200", description = "Causas-raiz encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CausaRaizResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<CausaRaizResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo, @Parameter(description = SwaggerParameterDescriptions.FILTRO_ACEITA) Boolean aceita, @Parameter(description = SwaggerParameterDescriptions.FILTRO_PRINCIPAL) Boolean principal, @Parameter(description = SwaggerParameterDescriptions.FILTRO_ID_PROBLEMA) Long idProblema);

    @Operation(summary = "Busca uma causa-raiz")
    @ApiResponse(responseCode = "200", description = "Causa-raiz encontrada", content = @Content(schema = @Schema(implementation = CausaRaizResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<CausaRaizResponseDTO> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_CAUSA_RAIZ) Long id);

    @Operation(summary = "Cria uma causa-raiz")
    @ApiResponse(responseCode = "201", description = "Causa-raiz criada", content = @Content(schema = @Schema(implementation = CausaRaizResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<CausaRaizResponseDTO> inserir(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo, @RequestBody(description = "Dados da causa-raiz", required = true) CausaRaizRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente uma causa-raiz")
    @ApiResponse(responseCode = "200", description = "Causa-raiz atualizada", content = @Content(schema = @Schema(implementation = CausaRaizResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<CausaRaizResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_CAUSA_RAIZ) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_CAUSA_RAIZ, required = true) Map<String, Object> campos);

    @Operation(summary = "Valida uma causa-raiz")
    @ApiResponse(responseCode = "200", description = "Causa-raiz validada", content = @Content(schema = @Schema(implementation = CausaRaizResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<CausaRaizResponseDTO> validar(@Parameter(description = SwaggerParameterDescriptions.ID_CAUSA_RAIZ) Long id, @Parameter(description = SwaggerParameterDescriptions.ID_USUARIO) Long idUsuario, @Parameter(description = SwaggerParameterDescriptions.ACEITA_CAUSA_RAIZ) Boolean aceita);

    @Operation(summary = "Exclui uma causa-raiz")
    @ApiResponse(responseCode = "204", description = "Causa-raiz excluída")
    @ApiResourceResponses
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_CAUSA_RAIZ) Long id);
}
