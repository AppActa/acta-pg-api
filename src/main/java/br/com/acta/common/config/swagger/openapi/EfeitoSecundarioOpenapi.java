package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioRequestDTO;
import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioResponseDTO;
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

@Tag(name = "Efeitos secundários", description = SwaggerOpenapiDescriptions.EFEITO_SECUNDARIO_CONTROLLER)
public interface EfeitoSecundarioOpenapi {

    @Operation(summary = "Lista os efeitos secundários de uma verificação")
    @ApiResponse(responseCode = "200", description = "Efeitos secundários encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EfeitoSecundarioResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<EfeitoSecundarioResponseDTO>> buscarEfeitosSecundarios(@Parameter(description = SwaggerParameterDescriptions.ID_RESULTADO) Long idResultado);

    @Operation(summary = "Cria um efeito secundário")
    @ApiResponse(responseCode = "201", description = "Efeito secundário criado", content = @Content(schema = @Schema(implementation = EfeitoSecundarioResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<EfeitoSecundarioResponseDTO> inserir(@Parameter(description = SwaggerParameterDescriptions.ID_RESULTADO) Long idResultado, @RequestBody(description = "Dados do efeito secundário", required = true) EfeitoSecundarioRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente um efeito secundário")
    @ApiResponse(responseCode = "200", description = "Efeito secundário atualizado", content = @Content(schema = @Schema(implementation = EfeitoSecundarioResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<EfeitoSecundarioResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_RESULTADO) Long idResultado, @Parameter(description = SwaggerParameterDescriptions.ID_EFEITO_SECUNDARIO) Long idEfeitoSecundario, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_EFEITO_SECUNDARIO, required = true) Map<String, Object> campos);

    @Operation(summary = "Exclui um efeito secundário")
    @ApiResponse(responseCode = "204", description = "Efeito secundário excluído")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_RESULTADO) Long idResultado, @Parameter(description = SwaggerParameterDescriptions.ID_EFEITO_SECUNDARIO) Long idEfeitoSecundario);
}
