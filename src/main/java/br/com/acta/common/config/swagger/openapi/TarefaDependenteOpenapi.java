package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.common.config.swagger.examples.SwaggerParameterDescriptions;
import br.com.acta.dto.pdca.tarefa.TarefaResponseDTO;
import br.com.acta.dto.pdca.tarefa.TarefaSummaryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Dependências de tarefas", description = SwaggerOpenapiDescriptions.TAREFA_DEPENDENTE_CONTROLLER)
public interface TarefaDependenteOpenapi {

    @Operation(summary = "Lista as dependências de uma tarefa")
    @ApiResponse(responseCode = "200", description = "Dependências encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TarefaSummaryResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<TarefaSummaryResponseDTO>> buscar(@Parameter(description = SwaggerParameterDescriptions.ID_TAREFA) Long id);

    @Operation(summary = "Adiciona uma dependência à tarefa")
    @ApiResponse(responseCode = "201", description = "Dependência adicionada", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<TarefaResponseDTO> adicionar(@Parameter(description = SwaggerParameterDescriptions.ID_TAREFA) Long id, @Parameter(description = SwaggerParameterDescriptions.ID_DEPENDENTE) Long idDependente);

    @Operation(summary = "Remove uma dependência da tarefa")
    @ApiResponse(responseCode = "204", description = "Dependência removida")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> remover(@Parameter(description = SwaggerParameterDescriptions.ID_TAREFA) Long id, @Parameter(description = SwaggerParameterDescriptions.ID_DEPENDENTE) Long idDependente);
}
