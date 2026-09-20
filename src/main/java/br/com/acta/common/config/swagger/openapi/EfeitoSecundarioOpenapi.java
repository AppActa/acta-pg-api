package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioRequestDTO;
import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

@Tag(name = "Efeitos secundários", description = SwaggerOpenapiDescriptions.EFEITO_SECUNDARIO_CONTROLLER)
public interface EfeitoSecundarioOpenapi {

    @Operation(summary = "Lista os efeitos secundários de uma verificação")
    @ApiResponse(responseCode = "200", description = "Efeitos secundários encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EfeitoSecundarioResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<EfeitoSecundarioResponseDTO>> buscarEfeitosSecundarios(Long idResultado);

    @Operation(summary = "Cria um efeito secundário")
    @ApiResponse(responseCode = "201", description = "Efeito secundário criado", content = @Content(schema = @Schema(implementation = EfeitoSecundarioResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<EfeitoSecundarioResponseDTO> inserir(Long idResultado, EfeitoSecundarioRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente um efeito secundário")
    @ApiResponse(responseCode = "200", description = "Efeito secundário atualizado", content = @Content(schema = @Schema(implementation = EfeitoSecundarioResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<EfeitoSecundarioResponseDTO> patch(Long idResultado, Long idEfeitoSecundario, Map<String, Object> campos);

    @Operation(summary = "Exclui um efeito secundário")
    @ApiResponse(responseCode = "204", description = "Efeito secundário excluído")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(Long idResultado, Long idEfeitoSecundario);
}
