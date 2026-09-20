package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.core.usuario.UsuarioSummaryResponseDTO;
import br.com.acta.dto.pdca.meta.MetaRequestDTO;
import br.com.acta.dto.pdca.meta.MetaResponseDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusMeta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Metas", description = SwaggerOpenapiDescriptions.META_CONTROLLER)
public interface MetaOpenapi {

    @Operation(summary = "Lista as metas de um ciclo")
    @ApiResponse(responseCode = "200", description = "Metas encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = MetaResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    ResponseEntity<List<MetaResponseDTO>> buscar(Long idCiclo, StatusMeta status, Prioridade prioridade);

    @Operation(summary = "Busca uma meta")
    @ApiResponse(responseCode = "200", description = "Meta encontrada", content = @Content(schema = @Schema(implementation = MetaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<MetaResponseDTO> buscar(Long id);

    @Operation(summary = "Cria uma meta")
    @ApiResponse(responseCode = "201", description = "Meta criada", content = @Content(schema = @Schema(implementation = MetaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<MetaResponseDTO> inserir(Long idPlanoAcao, MetaRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente uma meta")
    @ApiResponse(responseCode = "200", description = "Meta atualizada", content = @Content(schema = @Schema(implementation = MetaResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<MetaResponseDTO> patch(Long id, Map<String, Object> dto);

    @Operation(summary = "Atualiza o status de uma meta")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = MetaResponseDTO.class)))
    @ApiResourceResponses
    ResponseEntity<MetaResponseDTO> patchStatus(Long id, StatusMeta status);

    @Operation(summary = "Exclui uma meta")
    @ApiResponse(responseCode = "204", description = "Meta excluída")
    @ApiResourceResponses
    ResponseEntity<Void> delete(Long id);

    @Operation(summary = "Lista os responsáveis por uma meta")
    @ApiResponse(responseCode = "200", description = "Responsáveis encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioSummaryResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<UsuarioSummaryResponseDTO>> buscarResponsaveis(Long id);

    @Operation(summary = "Adiciona responsáveis a uma meta")
    @ApiResponse(responseCode = "201", description = "Responsáveis adicionados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UsuarioSummaryResponseDTO.class))))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<List<UsuarioSummaryResponseDTO>> adicionarResponsavel(List<Long> idsResponsaveis, Long id);

    @Operation(summary = "Remove responsáveis de uma meta")
    @ApiResponse(responseCode = "204", description = "Responsáveis removidos")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluirResponsaveis(Long id, List<Long> idsResponsaveis);
}
