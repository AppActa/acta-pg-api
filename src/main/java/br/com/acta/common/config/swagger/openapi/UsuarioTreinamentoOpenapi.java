package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoRequestDTO;
import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoResponseDTO;
import br.com.acta.entity.enums.StatusTreinamento;
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

@Tag(name = "Usuários de treinamentos", description = SwaggerOpenapiDescriptions.USUARIO_TREINAMENTO_CONTROLLER)
public interface UsuarioTreinamentoOpenapi {

    @Operation(summary = "Lista os usuários de um treinamento")
    @ApiResponse(responseCode = "200", description = "Usuários encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioTreinamentoResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<UsuarioTreinamentoResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_TREINAMENTO) Long id);

    @Operation(summary = "Adiciona um usuário ao treinamento")
    @ApiResponse(responseCode = "201", description = "Usuário adicionado", content = @Content(schema = @Schema(implementation = UsuarioTreinamentoResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<UsuarioTreinamentoResponseDTO> inserir(@Parameter(description = SwaggerParameterDescriptions.ID_TREINAMENTO) Long id, @RequestBody(description = "Dados do vínculo entre usuário e treinamento", required = true) UsuarioTreinamentoRequestDTO dto);

    @Operation(summary = "Atualiza o status do usuário no treinamento")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = UsuarioTreinamentoResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<UsuarioTreinamentoResponseDTO> patchStatus(@Parameter(description = SwaggerParameterDescriptions.ID_TREINAMENTO) Long id, @Parameter(description = SwaggerParameterDescriptions.ID_USUARIO) Long idUsuario, @Parameter(description = SwaggerParameterDescriptions.NOVO_STATUS) StatusTreinamento status);

    @Operation(summary = "Remove um usuário do treinamento")
    @ApiResponse(responseCode = "204", description = "Usuário removido")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_TREINAMENTO) Long id, @Parameter(description = SwaggerParameterDescriptions.ID_USUARIO) Long idUsuario);
}
