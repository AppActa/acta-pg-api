package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoRequestDTO;
import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoResponseDTO;
import br.com.acta.entity.enums.StatusTreinamento;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Usuários de treinamentos", description = SwaggerOpenapiDescriptions.USUARIO_TREINAMENTO_CONTROLLER)
public interface UsuarioTreinamentoOpenapi {

    @Operation(summary = "Lista os usuários de um treinamento")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioTreinamentoResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<UsuarioTreinamentoResponseDTO>> buscar(Long id);

    @Operation(summary = "Adiciona um usuário ao treinamento")
    @ApiResponse(responseCode = "201", description = "Usuário adicionado", content = @Content(schema = @Schema(implementation = UsuarioTreinamentoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<UsuarioTreinamentoResponseDTO> inserir(Long id, UsuarioTreinamentoRequestDTO dto);

    @Operation(summary = "Atualiza o status do usuário no treinamento")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = UsuarioTreinamentoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<UsuarioTreinamentoResponseDTO> patchStatus(Long id, Long idUsuario, StatusTreinamento status);

    @Operation(summary = "Remove um usuário do treinamento")
    @ApiResponse(responseCode = "204", description = "Usuário removido")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(Long id, Long idUsuario);
}
