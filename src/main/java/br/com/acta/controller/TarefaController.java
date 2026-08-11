package br.com.acta.controller;

import br.com.acta.dto.pdca.tarefa.TarefaRequestDTO;
import br.com.acta.dto.pdca.tarefa.TarefaResponseDTO;
import br.com.acta.dto.pdca.tarefa.TarefaStatusUpdateDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusTarefa;
import br.com.acta.service.TarefaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TarefaController {
    private final TarefaService service;

    @GetMapping("/plano-acao/{idPlanoAcao}/tarefa")
    public ResponseEntity<List<TarefaResponseDTO>> buscar(@PathVariable Long idPlanoAcao, @RequestParam(required = false) StatusTarefa status, @RequestParam(required = false) Long idResponsavel, @RequestParam(required = false) Prioridade prioridade) {
        List<TarefaResponseDTO> tarefas = service.buscar(idPlanoAcao, status, idResponsavel, prioridade);
        return ResponseEntity.ok(tarefas);
    }

    @GetMapping("/tarefa/{id}")
    public ResponseEntity<TarefaResponseDTO> buscar(@PathVariable @Positive Long id) {
        TarefaResponseDTO tarefa = service.buscar(id);
        return ResponseEntity.ok(tarefa);
    }

    @PostMapping("/plano-acao/{idPlanoAcao}/tarefa")
    public ResponseEntity<TarefaResponseDTO> inserir(@PathVariable @Positive Long idPlanoAcao, @Valid @RequestBody TarefaRequestDTO dto) {
        TarefaResponseDTO tarefa = service.inserir(idPlanoAcao, dto);
        return ResponseEntity.status(201).body(tarefa);
    }

    @PatchMapping("/tarefa/{id}")
    public ResponseEntity<TarefaResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        TarefaResponseDTO tarefa = service.patch(id, campos);
        return ResponseEntity.ok(tarefa);
    }

    @PatchMapping("/tarefa/{id}/status")
    public ResponseEntity<TarefaResponseDTO> patchStatus(@PathVariable @Positive Long id, @RequestBody @Valid TarefaStatusUpdateDTO dto) {
        TarefaResponseDTO tarefa = service.patchStatus(id, dto);
        return ResponseEntity.ok(tarefa);
    }

    @PatchMapping("/tarefa/{id}/reabrir")
    public ResponseEntity<TarefaResponseDTO> reabrir(@PathVariable @Positive Long id, @RequestParam LocalDate novoPrazo) {
        TarefaResponseDTO tarefa = service.reabrir(id, novoPrazo);
        return ResponseEntity.ok(tarefa);
    }

    @PatchMapping("/tarefa/{id}/reatribuir")
    public ResponseEntity<TarefaResponseDTO> reatribuir(@PathVariable @Positive Long id, @RequestParam Long idResponsavel) {
        TarefaResponseDTO tarefa = service.reatribuir(id, idResponsavel);
        return ResponseEntity.ok(tarefa);
    }

    @DeleteMapping("/tarefa/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
