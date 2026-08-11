package br.com.acta.controller;

import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HRequestDTO;
import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HResponseDTO;
import br.com.acta.service.Plano5W2HService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Validated
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class Plano5W2HController {
    private final Plano5W2HService service;

    @GetMapping("plano-acao/{idPlanoAcao}/5w2h")
    public ResponseEntity<Plano5W2HResponseDTO> buscar(@PathVariable @Positive Long idPlanoAcao) {
        Plano5W2HResponseDTO plano = service.buscarPorPlanoAcao(idPlanoAcao);
        return ResponseEntity.ok(plano);
    }

    @PostMapping("plano-acao/{idPlanoAcao}/5w2h")
    public ResponseEntity<Plano5W2HResponseDTO> inserir(@PathVariable @Positive Long idPlanoAcao, @Valid @RequestBody Plano5W2HRequestDTO dto) {
        Plano5W2HResponseDTO plano = service.inserir(dto, idPlanoAcao);
        return ResponseEntity.status(201).body(plano);
    }

    @PatchMapping("plano-acao/5w2h/{id}")
    public ResponseEntity<Plano5W2HResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        Plano5W2HResponseDTO plano = service.patch(id, campos);
        return ResponseEntity.ok(plano);
    }

    @DeleteMapping("plano-acao/5w2h/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}