package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaRequestDTO;
import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaResponseDTO;
import br.com.acta.dto.pdca.problema.ProblemaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    ResponseEntity<List<PriorizacaoProblemaResponseDTO>> buscar(Long idProblema, Long idUsuario);

    @Operation(summary = "Cria uma priorização")
    @ApiResponse(responseCode = "201", description = "Priorização criada", content = @Content(schema = @Schema(implementation = PriorizacaoProblemaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<PriorizacaoProblemaResponseDTO> inserir(Long idProblema, PriorizacaoProblemaRequestDTO dto);

    @Operation(summary = "Atualiza uma priorização")
    @ApiResponse(responseCode = "200", description = "Priorização atualizada", content = @Content(schema = @Schema(implementation = PriorizacaoProblemaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<PriorizacaoProblemaResponseDTO> patch(Long idProblema, Long idUsuario, Map<String, Object> campos);

    @Operation(summary = "Aplica o peso da priorização ao problema")
    @ApiResponse(responseCode = "200", description = "Peso aplicado", content = @Content(schema = @Schema(implementation = ProblemaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    ResponseEntity<ProblemaResponseDTO> aplicarPeso(Long idProblema);
}
