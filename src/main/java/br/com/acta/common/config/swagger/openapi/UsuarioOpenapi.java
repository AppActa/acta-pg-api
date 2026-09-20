package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioResponseDTO;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloResponseDTO;
import br.com.acta.entity.enums.TipoUsuario;
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

@Tag(name = "Usuários", description = SwaggerOpenapiDescriptions.USUARIO_CONTROLLER)
public interface UsuarioOpenapi {

    @Operation(summary = "Lista os ciclos de um usuário")
    @ApiResponse(responseCode = "200", description = "Ciclos encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioCicloResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<UsuarioCicloResponseDTO>> buscarCiclosUsuario(@Parameter(description = SwaggerParameterDescriptions.ID_USUARIO) Long idUsuario);

    @Operation(summary = "Lista os usuários de uma empresa")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<UsuarioResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_EMPRESA) Long idEmpresa, @Parameter(description = SwaggerParameterDescriptions.TIPO_USUARIO) TipoUsuario tipo);

    @Operation(summary = "Busca um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<UsuarioResponseDTO> buscarPorId(@Parameter(description = SwaggerParameterDescriptions.ID_USUARIO) Long id);

    @Operation(summary = "Cria um usuário")
    @ApiResponse(responseCode = "201", description = "Usuário criado", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<UsuarioResponseDTO> inserir(@RequestBody(description = "Dados do usuário", required = true) UsuarioRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente um usuário")
    @ApiResponse(responseCode = "200", description = "Usuário atualizado", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<UsuarioResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_USUARIO) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_USUARIO, required = true) Map<String, Object> campos);

    @Operation(summary = "Remove a foto de um usuário")
    @ApiResponse(responseCode = "200", description = "Foto removida", content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class)))
    @ApiResourceResponses
    ResponseEntity<UsuarioResponseDTO> excluirFoto(@Parameter(description = SwaggerParameterDescriptions.ID_USUARIO) Long id);

    @Operation(summary = "Inativa um usuário")
    @ApiResponse(responseCode = "204", description = "Usuário inativado")
    @ApiResourceResponses
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_USUARIO) Long id);
}
