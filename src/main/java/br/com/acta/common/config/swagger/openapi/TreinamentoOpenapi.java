package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.pdca.treinamento.TreinamentoRequestDTO;
import br.com.acta.dto.pdca.treinamento.TreinamentoResponseDTO;
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

@Tag(name = "Treinamentos", description = SwaggerOpenapiDescriptions.TREINAMENTO_CONTROLLER)
public interface TreinamentoOpenapi {

    @Operation(summary = "Lista os treinamentos de um ciclo")
    @ApiResponse(responseCode = "200", description = "Treinamentos encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TreinamentoResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<TreinamentoResponseDTO>> buscarTreinamentos(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo);

    @Operation(summary = "Busca um treinamento")
    @ApiResponse(responseCode = "200", description = "Treinamento encontrado", content = @Content(schema = @Schema(implementation = TreinamentoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<TreinamentoResponseDTO> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_TREINAMENTO) Long id);

    @Operation(summary = "Cria um treinamento")
    @ApiResponse(responseCode = "201", description = "Treinamento criado", content = @Content(schema = @Schema(implementation = TreinamentoResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<TreinamentoResponseDTO> inserir(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo, @RequestBody(description = "Dados do treinamento", required = true) TreinamentoRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente um treinamento")
    @ApiResponse(responseCode = "200", description = "Treinamento atualizado", content = @Content(schema = @Schema(implementation = TreinamentoResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<TreinamentoResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_TREINAMENTO) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_TREINAMENTO, required = true) Map<String, Object> campos);

    @Operation(summary = "Exclui um treinamento")
    @ApiResponse(responseCode = "204", description = "Treinamento excluído")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_TREINAMENTO) Long id);
}
