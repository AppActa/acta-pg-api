package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.pdca.ciclo.CicloRequestDTO;
import br.com.acta.dto.pdca.ciclo.CicloResponseDTO;
import br.com.acta.entity.enums.StatusCiclo;
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

@Tag(name = "Ciclos", description = SwaggerOpenapiDescriptions.CICLO_CONTROLLER)
public interface CicloOpenapi {

    @Operation(summary = "Lista os ciclos")
    @ApiResponse(responseCode = "200", description = "Ciclos encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CicloResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<CicloResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.FILTRO_ID_EMPRESA) Long idEmpresa, @Parameter(description = SwaggerParameterDescriptions.ID_GESTOR) Long idGestor, @Parameter(description = SwaggerParameterDescriptions.FILTRO_STATUS) StatusCiclo status);

    @Operation(summary = "Busca um ciclo")
    @ApiResponse(responseCode = "200", description = "Ciclo encontrado", content = @Content(schema = @Schema(implementation = CicloResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<CicloResponseDTO> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long id);

    @Operation(summary = "Cria um ciclo")
    @ApiResponse(responseCode = "201", description = "Ciclo criado", content = @Content(schema = @Schema(implementation = CicloResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<CicloResponseDTO> inserir(@RequestBody(description = "Dados do ciclo", required = true) CicloRequestDTO cicloRequest);

    @Operation(summary = "Atualiza parcialmente um ciclo")
    @ApiResponse(responseCode = "200", description = "Ciclo atualizado", content = @Content(schema = @Schema(implementation = CicloResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<CicloResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_CICLO, required = true) Map<String, Object> campos);

    @Operation(summary = "Atualiza o status de um ciclo")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = CicloResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<CicloResponseDTO> patchStatus(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long id, @Parameter(description = SwaggerParameterDescriptions.NOVO_STATUS) StatusCiclo status);

    @Operation(summary = "Cancela um ciclo")
    @ApiResponse(responseCode = "204", description = "Ciclo cancelado")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long id);

    @Operation(summary = "Consulta o avanço de um ciclo")
    @ApiResponse(responseCode = "200", description = "Percentual de avanço do ciclo", content = @Content(schema = @Schema(implementation = Double.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<Double> avancoCiclo(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long id);
}
