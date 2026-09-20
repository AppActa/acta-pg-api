package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.core.colaborador.ColaboradorRequestDTO;
import br.com.acta.dto.core.colaborador.ColaboradorResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Colaboradores", description = SwaggerOpenapiDescriptions.COLABORADOR_CONTROLLER)
public interface ColaboradorOpenapi {

    @Operation(summary = "Lista os colaboradores de uma empresa")
    @ApiResponse(responseCode = "200", description = "Colaboradores encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ColaboradorResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<ColaboradorResponseDTO>> buscarPorEmpresa(Long idEmpresa);

    @Operation(summary = "Lista os colaboradores")
    @ApiResponse(responseCode = "200", description = "Colaboradores encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ColaboradorResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<ColaboradorResponseDTO>> buscar();

    @Operation(summary = "Busca um colaborador")
    @ApiResponse(responseCode = "200", description = "Colaborador encontrado", content = @Content(schema = @Schema(implementation = ColaboradorResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<ColaboradorResponseDTO> buscar(Long id);

    @Operation(summary = "Cria um colaborador")
    @ApiResponse(responseCode = "201", description = "Colaborador criado", content = @Content(schema = @Schema(implementation = ColaboradorResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<ColaboradorResponseDTO> inserir(ColaboradorRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente um colaborador")
    @ApiResponse(responseCode = "200", description = "Colaborador atualizado", content = @Content(schema = @Schema(implementation = ColaboradorResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<ColaboradorResponseDTO> patch(Long id, Map<String, Object> dto);

    @Operation(summary = "Inativa um colaborador")
    @ApiResponse(responseCode = "204", description = "Colaborador inativado")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    ResponseEntity<Void> excluir(Long id);
}
