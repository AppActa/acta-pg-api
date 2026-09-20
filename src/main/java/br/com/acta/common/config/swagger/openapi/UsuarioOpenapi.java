package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioResponseDTO;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloResponseDTO;
import br.com.acta.entity.enums.TipoUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Usuários", description = SwaggerOpenapiDescriptions.USUARIO_CONTROLLER)
public interface UsuarioOpenapi {

    @Operation(summary = "Lista os ciclos de um usuário")
    @ApiResponse(responseCode = "200", description = "Ciclos encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioCicloResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<UsuarioCicloResponseDTO>> buscarCiclosUsuario(Long idUsuario);

    @Operation(summary = "Lista os usuários de uma empresa")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<UsuarioResponseDTO>> buscar(Long idEmpresa, TipoUsuario tipo);

    @Operation(summary = "Busca um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<UsuarioResponseDTO> buscarPorId(Long id);

    @Operation(summary = "Cria um usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<UsuarioResponseDTO> inserir(UsuarioRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<UsuarioResponseDTO> patch(Long id, Map<String, Object> campos);

    @Operation(summary = "Remove a foto de um usuário")
    @ApiResponse(responseCode = "200", description = "Foto removida", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    ResponseEntity<UsuarioResponseDTO> excluirFoto(Long id);

    @Operation(summary = "Inativa um usuário")
    @ApiResponse(responseCode = "204", description = "Usuário inativado")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    ResponseEntity<Void> excluir(Long id);
}
