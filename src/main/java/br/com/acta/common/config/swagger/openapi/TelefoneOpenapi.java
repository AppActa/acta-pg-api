package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
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

@Tag(name = "Telefones", description = SwaggerOpenapiDescriptions.TELEFONE_CONTROLLER)
public interface TelefoneOpenapi {

    @Operation(summary = "Lista os telefones de uma empresa")
    @ApiResponse(responseCode = "200", description = "Telefones encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TelefoneResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneEmpresa(@Parameter(description = SwaggerParameterDescriptions.ID_EMPRESA) Long idEmpresa);

    @Operation(summary = "Adiciona um telefone a uma empresa")
    @ApiResponse(responseCode = "201", description = "Telefone adicionado", content = @Content(schema = @Schema(implementation = TelefoneResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<TelefoneResponseDTO> inserirTelefoneEmpresa(@Parameter(description = SwaggerParameterDescriptions.ID_EMPRESA) Long idEmpresa, @RequestBody(description = "Dados do telefone", required = true) TelefoneRequestDTO dto);

    @Operation(summary = "Exclui um telefone de uma empresa")
    @ApiResponse(responseCode = "204", description = "Telefone excluído")
    @ApiResourceResponses
    ResponseEntity<Void> excluirTelefoneEmpresa(@Parameter(description = SwaggerParameterDescriptions.ID_EMPRESA) Long idEmpresa, @Parameter(description = SwaggerParameterDescriptions.ID_TELEFONE) Long idTelefone);

    @Operation(summary = "Lista os telefones de um colaborador")
    @ApiResponse(responseCode = "200", description = "Telefones encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TelefoneResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneColaborador(@Parameter(description = SwaggerParameterDescriptions.ID_COLABORADOR) Long idColaborador);

    @Operation(summary = "Adiciona um telefone a um colaborador")
    @ApiResponse(responseCode = "201", description = "Telefone adicionado", content = @Content(schema = @Schema(implementation = TelefoneResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<TelefoneResponseDTO> inserirTelefoneColaborador(@Parameter(description = SwaggerParameterDescriptions.ID_COLABORADOR) Long idColaborador, @RequestBody(description = "Dados do telefone", required = true) TelefoneRequestDTO dto);

    @Operation(summary = "Exclui um telefone de um colaborador")
    @ApiResponse(responseCode = "204", description = "Telefone excluído")
    @ApiResourceResponses
    ResponseEntity<Void> excluirTelefoneColaborador(@Parameter(description = SwaggerParameterDescriptions.ID_COLABORADOR) Long idColaborador, @Parameter(description = SwaggerParameterDescriptions.ID_TELEFONE) Long idTelefone);
}
