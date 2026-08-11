package br.com.acta.controller;

import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaRequestDTO;
import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaResponseDTO;
import br.com.acta.dto.pdca.problema.ProblemaResponseDTO;
import br.com.acta.service.PriorizacaoProblemaService;
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
@RequestMapping(value = "/problema/{idProblema}/priorizacao", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class PriorizacaoProblemaController {
    private final PriorizacaoProblemaService service;

    @GetMapping
    public ResponseEntity<List<PriorizacaoProblemaResponseDTO>> buscar(@PathVariable @Positive Long idProblema, @RequestParam(required = false) Long idUsuario) {
        List<PriorizacaoProblemaResponseDTO> priorizacao = service.buscar(idProblema, idUsuario);
        return ResponseEntity.ok(priorizacao);
    }

    @PostMapping
    public ResponseEntity<PriorizacaoProblemaResponseDTO> inserir(@PathVariable @Positive Long idProblema, @RequestBody @Valid PriorizacaoProblemaRequestDTO dto) {
        PriorizacaoProblemaResponseDTO priorizacao = service.inserir(idProblema, dto);
        return ResponseEntity.status(201).body(priorizacao);
    }

    @PatchMapping("/{idUsuario}")
    public ResponseEntity<PriorizacaoProblemaResponseDTO> patch(@PathVariable @Positive Long idProblema, @PathVariable @Positive Long idUsuario, @RequestBody Map<String, Object> campos) {
        PriorizacaoProblemaResponseDTO priorizacao = service.patch(idProblema, idUsuario, campos);
        return ResponseEntity.ok(priorizacao);
    }

    @PatchMapping("/aplicar-peso")
    public ResponseEntity<ProblemaResponseDTO> aplicarPeso(@PathVariable @Positive Long idProblema){
        ProblemaResponseDTO priorizacao = service.aplicarPeso(idProblema);
        return ResponseEntity.ok(priorizacao);
    }
}
