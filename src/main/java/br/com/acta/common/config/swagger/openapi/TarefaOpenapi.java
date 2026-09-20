package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiBusinessRuleResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.pdca.tarefa.TarefaRequestDTO;
import br.com.acta.dto.pdca.tarefa.TarefaResponseDTO;
import br.com.acta.dto.pdca.tarefa.TarefaStatusUpdateDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusTarefa;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Tag(name = "Tarefas", description = SwaggerOpenapiDescriptions.TAREFA_CONTROLLER)
public interface TarefaOpenapi {

    @Operation(summary = "Lista as tarefas de um plano de ação")
    @ApiResponse(responseCode = "200", description = "Tarefas encontradas", content = @Content(array = @ArraySchema(schema = @Schema(implementation = TarefaResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<TarefaResponseDTO>> buscar(Long idPlanoAcao, StatusTarefa status, Long idResponsavel, Prioridade prioridade);

    @Operation(summary = "Busca uma tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa encontrada", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<TarefaResponseDTO> buscar(Long id);

    @Operation(summary = "Cria uma tarefa")
    @ApiResponse(responseCode = "201", description = "Tarefa criada", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<TarefaResponseDTO> inserir(Long idPlanoAcao, TarefaRequestDTO dto);

    @Operation(summary = "Atualiza parcialmente uma tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<TarefaResponseDTO> patch(Long id, Map<String, Object> campos);

    @Operation(summary = "Atualiza o status de uma tarefa")
    @ApiResponse(responseCode = "200", description = "Status atualizado", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<TarefaResponseDTO> patchStatus(Long id, TarefaStatusUpdateDTO dto);

    @Operation(summary = "Reabre uma tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa reaberta", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<TarefaResponseDTO> reabrir(Long id, LocalDate novoPrazo);

    @Operation(summary = "Reatribui uma tarefa")
    @ApiResponse(responseCode = "200", description = "Tarefa reatribuída", content = @Content(schema = @Schema(implementation = TarefaResponseDTO.class)))
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<TarefaResponseDTO> reatribuir(Long id, Long idResponsavel);

    @Operation(summary = "Exclui uma tarefa")
    @ApiResponse(responseCode = "204", description = "Tarefa excluída")
    @ApiResourceResponses
    @ApiBusinessRuleResponse
    ResponseEntity<Void> excluir(Long id);
}
