package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaRequestDTO;
import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaResponseDTO;
import br.com.acta.dto.pdca.problema.ProblemaResponseDTO;
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

@Tag(name = "Priorização de problemas", description = SwaggerOpenapiDescriptions.PRIORIZACAO_PROBLEMA_CONTROLLER)
public interface PriorizacaoProblemaOpenapi {

    @Operation(summary = "Lista as priorizações de um problema")
    @ApiResponse(responseCode = "200", description = "Priorizações encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PriorizacaoProblemaResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiBusinessRuleResponse
    ResponseEntity<List<PriorizacaoProblemaResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_PROBLEMA) Long idProblema, @Parameter(description = SwaggerParameterDescriptions.FILTRO_ID_USUARIO) Long idUsuario);

    @Operation(summary = "Cria uma priorização")
    @ApiResponse(responseCode = "201", description = "Priorização criada", content = @Content(schema = @Schema(implementation = PriorizacaoProblemaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<PriorizacaoProblemaResponseDTO> inserir(@Parameter(description = SwaggerParameterDescriptions.ID_PROBLEMA) Long idProblema, @RequestBody(description = "Dados da priorização", required = true) PriorizacaoProblemaRequestDTO dto);

    @Operation(summary = "Atualiza uma priorização")
    @ApiResponse(responseCode = "200", description = "Priorização atualizada", content = @Content(schema = @Schema(implementation = PriorizacaoProblemaResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<PriorizacaoProblemaResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_PROBLEMA) Long idProblema, @Parameter(description = SwaggerParameterDescriptions.ID_USUARIO) Long idUsuario, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_PRIORIZACAO, required = true) Map<String, Object> campos);

    @Operation(summary = "Aplica o peso da priorização ao problema")
    @ApiResponse(responseCode = "200", description = "Peso aplicado", content = @Content(schema = @Schema(implementation = ProblemaResponseDTO.class)))
    @ApiResourceResponses
    ResponseEntity<ProblemaResponseDTO> aplicarPeso(@Parameter(description = SwaggerParameterDescriptions.ID_PROBLEMA) Long idProblema);
}
