package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Telefones", description = SwaggerOpenapiDescriptions.TELEFONE_CONTROLLER)
public interface TelefoneOpenapi {

    @Operation(summary = "Lista os telefones de uma empresa")
    @ApiResponse(responseCode = "200", description = "Telefones encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TelefoneResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneEmpresa(Long idEmpresa);

    @Operation(summary = "Adiciona um telefone a uma empresa")
    @ApiResponse(responseCode = "201", description = "Telefone adicionado", content = @Content(schema = @Schema(implementation = TelefoneResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<TelefoneResponseDTO> inserirTelefoneEmpresa(Long idEmpresa, TelefoneRequestDTO dto);

    @Operation(summary = "Exclui um telefone de uma empresa")
    @ApiResponse(responseCode = "204", description = "Telefone excluído")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    ResponseEntity<Void> excluirTelefoneEmpresa(Long idEmpresa, Long idTelefone);

    @Operation(summary = "Lista os telefones de um colaborador")
    @ApiResponse(responseCode = "200", description = "Telefones encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TelefoneResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneColaborador(Long idColaborador);

    @Operation(summary = "Adiciona um telefone a um colaborador")
    @ApiResponse(responseCode = "201", description = "Telefone adicionado", content = @Content(schema = @Schema(implementation = TelefoneResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<TelefoneResponseDTO> inserirTelefoneColaborador(Long idColaborador, TelefoneRequestDTO dto);

    @Operation(summary = "Exclui um telefone de um colaborador")
    @ApiResponse(responseCode = "204", description = "Telefone excluído")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    ResponseEntity<Void> excluirTelefoneColaborador(Long idColaborador, Long idTelefone);
}
