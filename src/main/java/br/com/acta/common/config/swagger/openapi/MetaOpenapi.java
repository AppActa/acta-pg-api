package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.core.usuario.UsuarioSummaryResponseDTO;
import br.com.acta.dto.pdca.meta.MetaRequestDTO;
import br.com.acta.dto.pdca.meta.MetaResponseDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusMeta;
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

@Tag(name = "Metas", description = SwaggerOpenapiDescriptions.META_CONTROLLER)
public interface MetaOpenapi {

    @Operation(summary = "Lista as metas de um ciclo")
    @ApiResponse(responseCode = "200", description = "Metas encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MetaResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<MetaResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo, @Parameter(description = SwaggerParameterDescriptions.FILTRO_STATUS) StatusMeta status, @Parameter(description = SwaggerParameterDescriptions.FILTRO_PRIORIDADE) Prioridade prioridade);

    @Operation(summary = "Busca uma meta")
    @ApiResponse(responseCode = "200", description = "Meta encontrada", content = @Content(schema = @Schema(implementation = MetaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<MetaResponseDTO> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_META) Long id);

    @Operation(summary = "Cria uma meta")
    @ApiResponse(responseCode = "201", description = "Meta criada", content = @Content(schema = @Schema(implementation = MetaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<MetaResponseDTO> inserir(@Parameter(description = SwaggerParameterDescriptions.ID_PLANO_ACAO) Long idPlanoAcao, @RequestBody(description = "Dados da meta", required = true) MetaRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente uma meta")
    @ApiResponse(responseCode = "200", description = "Meta atualizada", content = @Content(schema = @Schema(implementation = MetaResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<MetaResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_META) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_META, required = true) Map<String, Object> dto);

    @Operation(summary = "Atualiza o status de uma meta")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = MetaResponseDTO.class)))
    @ApiResourceResponses
    ResponseEntity<MetaResponseDTO> patchStatus(@Parameter(description = SwaggerParameterDescriptions.ID_META) Long id, @Parameter(description = SwaggerParameterDescriptions.NOVO_STATUS) StatusMeta status);

    @Operation(summary = "Exclui uma meta")
    @ApiResponse(responseCode = "204", description = "Meta excluída")
    @ApiResourceResponses
    ResponseEntity<Void> delete(@Parameter(description = SwaggerParameterDescriptions.ID_META) Long id);

    @Operation(summary = "Lista os responsáveis por uma meta")
    @ApiResponse(responseCode = "200", description = "Responsáveis encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioSummaryResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<UsuarioSummaryResponseDTO>> buscarResponsaveis(@Parameter(description = SwaggerParameterDescriptions.ID_META) Long id);

    @Operation(summary = "Adiciona responsáveis a uma meta")
    @ApiResponse(responseCode = "201", description = "Responsáveis adicionados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioSummaryResponseDTO.class))))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<List<UsuarioSummaryResponseDTO>> adicionarResponsavel(@Parameter(description = SwaggerParameterDescriptions.IDS_RESPONSAVEIS) List<Long> idsResponsaveis, @Parameter(description = SwaggerParameterDescriptions.ID_META) Long id);

    @Operation(summary = "Remove responsáveis de uma meta")
    @ApiResponse(responseCode = "204", description = "Responsáveis removidos")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluirResponsaveis(@Parameter(description = SwaggerParameterDescriptions.ID_META) Long id, @Parameter(description = SwaggerParameterDescriptions.IDS_RESPONSAVEIS) List<Long> idsResponsaveis);
}
