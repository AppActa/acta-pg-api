package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoRequestDTO;
import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoResponseDTO;
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

@Tag(name = "Verificações de resultado", description = SwaggerOpenapiDescriptions.VERIFICACAO_RESULTADO_CONTROLLER)
public interface VerificacaoResultadoOpenapi {

    @Operation(summary = "Lista as verificações de um ciclo")
    @ApiResponse(responseCode = "200", description = "Verificações encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = VerificacaoResultadoResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<VerificacaoResultadoResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo);

    @Operation(summary = "Busca uma verificação")
    @ApiResponse(responseCode = "200", description = "Verificação encontrada", content = @Content(schema = @Schema(implementation = VerificacaoResultadoResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<VerificacaoResultadoResponseDTO> buscarPorId(@Parameter(description = SwaggerParameterDescriptions.ID_RESULTADO) Long id);

    @Operation(summary = "Cria uma verificação")
    @ApiResponse(responseCode = "201", description = "Verificação criada", content = @Content(schema = @Schema(implementation = VerificacaoResultadoResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<VerificacaoResultadoResponseDTO> inserir(@Parameter(description = SwaggerParameterDescriptions.ID_CICLO) Long idCiclo, @Parameter(description = SwaggerParameterDescriptions.ID_CRIADO_POR) Long idCriadoPor, @RequestBody(description = "Dados da verificação de resultado", required = true) VerificacaoResultadoRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente uma verificação")
    @ApiResponse(responseCode = "200", description = "Verificação atualizada", content = @Content(schema = @Schema(implementation = VerificacaoResultadoResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<VerificacaoResultadoResponseDTO> patch(@Parameter(description = SwaggerParameterDescriptions.ID_RESULTADO) Long id, @RequestBody(description = SwaggerParameterDescriptions.CAMPOS_VERIFICACAO, required = true) Map<String, Object> campos);

    @Operation(summary = "Exclui uma verificação")
    @ApiResponse(responseCode = "204", description = "Verificação excluída")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(@Parameter(description = SwaggerParameterDescriptions.ID_RESULTADO) Long id);
}
