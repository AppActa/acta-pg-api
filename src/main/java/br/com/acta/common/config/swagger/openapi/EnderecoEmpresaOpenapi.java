package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.core.empresa.endereco.EnderecoRequestDTO;
import br.com.acta.dto.core.empresa.endereco.EnderecoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Endereços de empresas", description = SwaggerOpenapiDescriptions.ENDERECO_EMPRESA_CONTROLLER)
public interface EnderecoEmpresaOpenapi {

    @Operation(summary = "Busca um endereço de uma empresa")
    @ApiResponse(responseCode = "200", description = "Endereço encontrado", content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<EnderecoResponseDTO> buscar(Long idEmpresa, Long idEndereco);

    @Operation(summary = "Lista os endereços de uma empresa")
    @ApiResponse(responseCode = "200", description = "Endereços encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EnderecoResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<EnderecoResponseDTO>> buscar(Long idEmpresa);

    @Operation(summary = "Adiciona um endereço a uma empresa")
    @ApiResponse(responseCode = "201", description = "Endereço adicionado", content = @Content(schema = @Schema(implementation = EnderecoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<EnderecoResponseDTO> inserir(Long idEmpresa, EnderecoRequestDTO dto);

    @Operation(summary = "Exclui um endereço de uma empresa")
    @ApiResponse(responseCode = "204", description = "Endereço excluído")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    ResponseEntity<Void> excluir(Long idEmpresa, Long idEndereco);
}
