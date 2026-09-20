package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.pdca.problema.ProblemaRequestDTO;
import br.com.acta.dto.pdca.problema.ProblemaResponseDTO;
import br.com.acta.entity.enums.StatusProblema;
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

@Tag(name = "Problemas", description = SwaggerOpenapiDescriptions.PROBLEMA_CONTROLLER)
public interface ProblemaOpenapi {

    @Operation(summary = "Lista os problemas de um ciclo")
    @ApiResponse(responseCode = "200", description = "Problemas encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ProblemaResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<ProblemaResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo, @Parameter(description = SwaggerParameterDescriptions.FILTRO_STATUS) StatusProblema status, @Parameter(description = SwaggerParameterDescriptions.ID_PROBLEMA_PAI) Long idProblemaPai);

    @Operation(summary = "Busca um problema")
    @ApiResponse(responseCode = "200", description = "Problema encontrado", content = @Content(schema = @Schema(implementation = ProblemaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<ProblemaResponseDTO> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_PROBLEMA) Long id);

    @Operation(summary = "Cria um problema")
    @ApiResponse(responseCode = "201", description = "Problema criado", content = @Content(schema = @Schema(implementation = ProblemaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<ProblemaResponseDTO> inserir(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo, @RequestBody(description = "Dados do problema", required = true) ProblemaRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente um problema")
    @ApiResponse(responseCode = "200", description = "Problema atualizado", content = @Content(schema = @Schema(implementation = ProblemaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<ProblemaResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_PROBLEMA) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_PROBLEMA, required = true) Map<String, Object> campos);

    @Operation(summary = "Atualiza o status de um problema")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = ProblemaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<ProblemaResponseDTO> patchStatus(@Parameter(description = SwaggerParameterDescriptions.ID_PROBLEMA) Long id, @Parameter(description = SwaggerParameterDescriptions.NOVO_STATUS) StatusProblema status);

    @Operation(summary = "Exclui um problema")
    @ApiResponse(responseCode = "204", description = "Problema excluído")
    @ApiResourceResponses
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_PROBLEMA) Long id);
}
