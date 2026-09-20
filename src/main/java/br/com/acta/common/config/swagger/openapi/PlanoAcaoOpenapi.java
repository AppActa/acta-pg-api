package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.pdca.plano_acao.PlanoAcaoRequestDTO;
import br.com.acta.dto.pdca.plano_acao.PlanoAcaoResponseDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
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

@Tag(name = "Planos de ação", description = SwaggerOpenapiDescriptions.PLANO_ACAO_CONTROLLER)
public interface PlanoAcaoOpenapi {

    @Operation(summary = "Lista os planos de ação de um ciclo")
    @ApiResponse(responseCode = "200", description = "Planos de ação encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = PlanoAcaoResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<PlanoAcaoResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo, @Parameter(description = SwaggerParameterDescriptions.FILTRO_STATUS) StatusPlanoAcao status, @Parameter(description = SwaggerParameterDescriptions.FILTRO_PRIORIDADE) Prioridade prioridade);

    @Operation(summary = "Busca um plano de ação")
    @ApiResponse(responseCode = "200", description = "Plano de ação encontrado", content = @Content(schema = @Schema(implementation = PlanoAcaoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<PlanoAcaoResponseDTO> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_PLANO_ACAO) Long id);

    @Operation(summary = "Cria um plano de ação")
    @ApiResponse(responseCode = "201", description = "Plano de ação criado", content = @Content(schema = @Schema(implementation = PlanoAcaoResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<PlanoAcaoResponseDTO> inserir(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo, @RequestBody(description = "Dados do plano de ação", required = true) PlanoAcaoRequestDTO dto, @Parameter(description = SwaggerParameterDescriptions.ID_CRIADO_POR) Long idCriadoPor);

    @Operation(summary = "Atualiza parcialmente um plano de ação")
    @ApiResponse(responseCode = "200", description = "Plano de ação atualizado", content = @Content(schema = @Schema(implementation = PlanoAcaoResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<PlanoAcaoResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_PLANO_ACAO) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_PLANO_ACAO, required = true) Map<String, Object> campos);

    @Operation(summary = "Atualiza o status de um plano de ação")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = PlanoAcaoResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<PlanoAcaoResponseDTO> patchStatus(@Parameter(description = SwaggerParameterDescriptions.ID_PLANO_ACAO) Long id, @Parameter(description = SwaggerParameterDescriptions.NOVO_STATUS) StatusPlanoAcao status);

    @Operation(summary = "Exclui um plano de ação")
    @ApiResponse(responseCode = "204", description = "Plano de ação excluído")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_PLANO_ACAO) Long id);
}
