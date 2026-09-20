package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.core.colaborador.ColaboradorRequestDTO;
import br.com.acta.dto.core.colaborador.ColaboradorResponseDTO;
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

@Tag(name = "Colaboradores", description = SwaggerOpenapiDescriptions.COLABORADOR_CONTROLLER)
public interface ColaboradorOpenapi {

    @Operation(summary = "Lista os colaboradores de uma empresa")
    @ApiResponse(responseCode = "200", description = "Colaboradores encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = ColaboradorResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<ColaboradorResponseDTO>> buscarPorEmpresa(@Parameter(description = SwaggerParameterDescriptions.ID_EMPRESA) Long idEmpresa);

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
    ResponseEntity<ColaboradorResponseDTO> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_COLABORADOR) Long id);

    @Operation(summary = "Cria um colaborador")
    @ApiResponse(responseCode = "201", description = "Colaborador criado", content = @Content(schema = @Schema(implementation = ColaboradorResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<ColaboradorResponseDTO> inserir(@RequestBody(description = "Dados do colaborador", required = true) ColaboradorRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente um colaborador")
    @ApiResponse(responseCode = "200", description = "Colaborador atualizado", content = @Content(schema = @Schema(implementation = ColaboradorResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<ColaboradorResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_COLABORADOR) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_COLABORADOR, required = true) Map<String, Object> dto);

    @Operation(summary = "Inativa um colaborador")
    @ApiResponse(responseCode = "204", description = "Colaborador inativado")
    @ApiResourceResponses
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_COLABORADOR) Long id);
}
