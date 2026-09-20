package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloRequestDTO;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloResponseDTO;
import br.com.acta.entity.enums.PapelCiclo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Usuários do ciclo", description = SwaggerOpenapiDescriptions.USUARIO_CICLO_CONTROLLER)
public interface UsuarioCicloOpenapi {

    @Operation(summary = "Lista os usuários de um ciclo")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioCicloResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<UsuarioCicloResponseDTO>> buscar(Long idCiclo);

    @Operation(summary = "Adiciona um usuário ao ciclo")
    @ApiResponse(responseCode = "201", description = "Usuário adicionado", content = @Content(schema = @Schema(implementation = UsuarioCicloResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<UsuarioCicloResponseDTO> inserir(Long idCiclo, UsuarioCicloRequestDTO dto);

    @Operation(summary = "Atualiza o papel de um usuário no ciclo")
    @ApiResponse(responseCode = "200", description = "Papel atualizado", content = @Content(schema = @Schema(implementation = UsuarioCicloResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<UsuarioCicloResponseDTO> patch(Long idCiclo, Long idUsuario, PapelCiclo papelCiclo);

    @Operation(summary = "Substitui o responsável pelo ciclo")
    @ApiResponse(responseCode = "200", description = "Responsável substituído", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioCicloResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<List<UsuarioCicloResponseDTO>> substituirResponsavel(Long idCiclo, Long idUsuarioAntigo, Long idUsuarioNovo);

    @Operation(summary = "Remove um usuário do ciclo")
    @ApiResponse(responseCode = "204", description = "Usuário removido")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(Long idCiclo, Long idUsuario);
}
