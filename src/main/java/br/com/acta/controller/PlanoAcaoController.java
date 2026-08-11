package br.com.acta.controller;

import br.com.acta.dto.pdca.plano_acao.PlanoAcaoRequestDTO;
import br.com.acta.dto.pdca.plano_acao.PlanoAcaoResponseDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
import br.com.acta.service.PlanoAcaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PlanoAcaoController {
    private final PlanoAcaoService service;

    @GetMapping("/ciclo/{idCiclo}/plano-acao")
    public ResponseEntity<List<PlanoAcaoResponseDTO>> buscar(@PathVariable @Positive Long idCiclo, @RequestParam(required = false) StatusPlanoAcao status, @RequestParam(required = false) Prioridade prioridade) {
        List<PlanoAcaoResponseDTO> planos = service.buscar(idCiclo, status, prioridade);
        return ResponseEntity.ok(planos);
    }

    @GetMapping("/plano-acao/{id}")
    public ResponseEntity<PlanoAcaoResponseDTO> buscar(@PathVariable @Positive Long id) {
        PlanoAcaoResponseDTO plano = service.buscar(id);
        return ResponseEntity.ok(plano);
    }

    @PostMapping("/ciclo/{idCiclo}/plano-acao")
    public ResponseEntity<PlanoAcaoResponseDTO> inserir(@PathVariable @Positive Long idCiclo, @Valid @RequestBody PlanoAcaoRequestDTO dto, @RequestParam @Positive Long idCriadoPor) {
        PlanoAcaoResponseDTO plano = service.inserir(dto, idCiclo, idCriadoPor);
        return ResponseEntity.status(201).body(plano);
    }

    @PatchMapping("/plano-acao/{id}")
    public ResponseEntity<PlanoAcaoResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        PlanoAcaoResponseDTO plano = service.patch(id, campos);
        return ResponseEntity.ok(plano);
    }

    @PatchMapping("/plano-acao/{id}/status")
    public ResponseEntity<PlanoAcaoResponseDTO> patchStatus(@PathVariable @Positive Long id, @RequestParam StatusPlanoAcao status) {
        PlanoAcaoResponseDTO plano = service.patchStatus(id, status);
        return ResponseEntity.ok(plano);
    }

    @DeleteMapping("/plano-acao/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
