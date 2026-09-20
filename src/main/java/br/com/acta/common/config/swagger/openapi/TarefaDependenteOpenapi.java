package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.pdca.tarefa.TarefaResponseDTO;
import br.com.acta.dto.pdca.tarefa.TarefaSummaryResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
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
    ResponseEntity<List<TarefaSummaryResponseDTO>> buscar(Long id);

    @Operation(summary = "Adiciona uma dependência à tarefa")
    @ApiResponse(responseCode = "201", description = "Dependência adicionada", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<TarefaResponseDTO> adicionar(Long id, Long idDependente);

    @Operation(summary = "Remove uma dependência da tarefa")
    @ApiResponse(responseCode = "204", description = "Dependência removida")
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    @ApiConflictResponse
    @ApiBusinessRuleResponse
    ResponseEntity<Void> remover(Long id, Long idDependente);
}
