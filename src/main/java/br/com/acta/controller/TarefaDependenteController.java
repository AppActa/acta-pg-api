package br.com.acta.controller;

import br.com.acta.common.config.swagger.openapi.TarefaDependenteOpenapi;
import br.com.acta.dto.pdca.tarefa.TarefaResponseDTO;
import br.com.acta.dto.pdca.tarefa.TarefaSummaryResponseDTO;
import br.com.acta.service.TarefaService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/tarefa/{id}/dependencia", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TarefaDependenteController implements TarefaDependenteOpenapi {
    private final TarefaService service;

    @GetMapping
    @Override
    public ResponseEntity<List<TarefaSummaryResponseDTO>> buscar(@PathVariable @Positive Long id) {
        List<TarefaSummaryResponseDTO> dependentes = service.buscarDependentes(id);
        return ResponseEntity.ok(dependentes);
    }

    @PostMapping("/{idDependente}")
    @Override
    public ResponseEntity<TarefaResponseDTO> adicionar(@PathVariable @Positive Long id, @PathVariable @Positive Long idDependente){
        TarefaResponseDTO tarefa = service.adicionarDependencia(id, idDependente);
        return ResponseEntity.status(201).body(tarefa);
    }

    @DeleteMapping("/{idDependente}")
    @Override
    public ResponseEntity<Void> remover(@PathVariable @Positive Long id, @PathVariable @Positive Long idDependente){
        service.removerDependencia(id, idDependente);
        return ResponseEntity.noContent().build();
    }
}
