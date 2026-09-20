package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.core.empresa.EmpresaRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaResponseDTO;
import br.com.acta.entity.enums.TamanhoEmpresa;
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

@Tag(name = "Empresas", description = SwaggerOpenapiDescriptions.EMPRESA_CONTROLLER)
public interface EmpresaOpenapi {

    @Operation(summary = "Lista as empresas")
    @ApiResponse(responseCode = "200", description = "Empresas encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmpresaResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<EmpresaResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.TAMANHO_EMPRESA) TamanhoEmpresa tamanho);

    @Operation(summary = "Busca uma empresa")
    @ApiResponse(responseCode = "200", description = "Empresa encontrada", content = @Content(schema = @Schema(implementation = EmpresaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<EmpresaResponseDTO> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_EMPRESA) Long id);

    @Operation(summary = "Cria uma empresa")
    @ApiResponse(responseCode = "201", description = "Empresa criada", content = @Content(schema = @Schema(implementation = EmpresaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiConflictResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<EmpresaResponseDTO> inserir(@RequestBody(description = "Dados da empresa", required = true) EmpresaRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente uma empresa")
    @ApiResponse(responseCode = "200", description = "Empresa atualizada", content = @Content(schema = @Schema(implementation = EmpresaResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<EmpresaResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_EMPRESA) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_EMPRESA, required = true) Map<String, Object> campos);

    @Operation(summary = "Inativa uma empresa")
    @ApiResponse(responseCode = "204", description = "Empresa inativada")
    @ApiResourceResponses
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_EMPRESA) Long id);
}
