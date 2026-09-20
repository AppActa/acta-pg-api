package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.pdca.plano_acao.PlanoAcaoRequestDTO;
import br.com.acta.dto.pdca.plano_acao.PlanoAcaoResponseDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Planos de ação", description = SwaggerOpenapiDescriptions.PLANO_ACAO_CONTROLLER)
public interface PlanoAcaoOpenapi {

    @Operation(summary = "Lista os planos de ação de um ciclo")
    @ApiResponse(responseCode = "200", description = "Planos de ação encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanoAcaoResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<PlanoAcaoResponseDTO>> buscar(Long idCiclo, StatusPlanoAcao status, Prioridade prioridade);

    @Operation(summary = "Busca um plano de ação")
    @ApiResponse(responseCode = "200", description = "Plano de ação encontrado", content = @Content(schema = @Schema(implementation = PlanoAcaoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<PlanoAcaoResponseDTO> buscar(Long id);

    @Operation(summary = "Cria um plano de ação")
    @ApiResponse(responseCode = "201", description = "Plano de ação criado", content = @Content(schema = @Schema(implementation = PlanoAcaoResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<PlanoAcaoResponseDTO> inserir(Long idCiclo, PlanoAcaoRequestDTO dto, Long idCriadoPor);

    @Operation(summary = "Atualiza parcialmente um plano de ação")
    @ApiResponse(responseCode = "200", description = "Plano de ação atualizado", content = @Content(schema = @Schema(implementation = PlanoAcaoResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<PlanoAcaoResponseDTO> patch(Long id, Map<String, Object> campos);

    @Operation(summary = "Atualiza o status de um plano de ação")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = PlanoAcaoResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<PlanoAcaoResponseDTO> patchStatus(Long id, StatusPlanoAcao status);

    @Operation(summary = "Exclui um plano de ação")
    @ApiResponse(responseCode = "204", description = "Plano de ação excluído")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(Long id);
}
