package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.pdca.ciclo.CicloRequestDTO;
import br.com.acta.dto.pdca.ciclo.CicloResponseDTO;
import br.com.acta.entity.enums.StatusCiclo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Ciclos", description = SwaggerOpenapiDescriptions.CICLO_CONTROLLER)
public interface CicloOpenapi {

    @Operation(summary = "Lista os ciclos")
    @ApiResponse(responseCode = "200", description = "Ciclos encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CicloResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<CicloResponseDTO>> buscar(Long idEmpresa, Long idGestor, StatusCiclo status);

    @Operation(summary = "Busca um ciclo")
    @ApiResponse(responseCode = "200", description = "Ciclo encontrado", content = @Content(schema = @Schema(implementation = CicloResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<CicloResponseDTO> buscar(Long id);

    @Operation(summary = "Cria um ciclo")
    @ApiResponse(responseCode = "201", description = "Ciclo criado", content = @Content(schema = @Schema(implementation = CicloResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<CicloResponseDTO> inserir(CicloRequestDTO cicloRequest);

    @Operation(summary = "Atualiza parcialmente um ciclo")
    @ApiResponse(responseCode = "200", description = "Ciclo atualizado", content = @Content(schema = @Schema(implementation = CicloResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<CicloResponseDTO> patch(Long id, Map<String, Object> campos);

    @Operation(summary = "Atualiza o status de um ciclo")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = CicloResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<CicloResponseDTO> patchStatus(Long id, StatusCiclo status);

    @Operation(summary = "Cancela um ciclo")
    @ApiResponse(responseCode = "204", description = "Ciclo cancelado")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(Long id);

    @Operation(summary = "Consulta o avanço de um ciclo")
    @ApiResponse(responseCode = "200", description = "Percentual de avanço do ciclo", content = @Content(schema = @Schema(implementation = Double.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<Double> avancoCiclo(Long id);
}
