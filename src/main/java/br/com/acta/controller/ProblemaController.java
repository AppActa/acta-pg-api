package br.com.acta.controller;

import br.com.acta.dto.pdca.problema.ProblemaRequestDTO;
import br.com.acta.dto.pdca.problema.ProblemaResponseDTO;
import br.com.acta.entity.enums.StatusProblema;
import br.com.acta.service.ProblemaService;
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
public class ProblemaController {
    private final ProblemaService service;

    @GetMapping("/ciclo/{idCiclo}/problema")
    public ResponseEntity<List<ProblemaResponseDTO>> buscar(@PathVariable @Positive Long idCiclo, @RequestParam(required = false) StatusProblema status, @RequestParam(required = false) @Positive Long idProblemaPai) {
        List<ProblemaResponseDTO> problemas = service.buscar(idCiclo, status, idProblemaPai);
        return ResponseEntity.ok(problemas);
    }

    @GetMapping("/problema/{id}")
    public ResponseEntity<ProblemaResponseDTO> buscar(@PathVariable @Positive Long id) {
        ProblemaResponseDTO problema = service.buscar(id);
        return ResponseEntity.ok(problema);
    }

    @PostMapping("/ciclos/{idCiclo}/problema")
    public ResponseEntity<ProblemaResponseDTO> inserir(@PathVariable @Positive Long idCiclo, @RequestBody ProblemaRequestDTO dto) {
        ProblemaResponseDTO problema = service.inserir(dto, idCiclo);
        return ResponseEntity.status(201).body(problema);
    }

    @PatchMapping("/problema/{id}")
    public ResponseEntity<ProblemaResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        ProblemaResponseDTO problema = service.patch(id, campos);
        return ResponseEntity.ok(problema);
    }

    @PatchMapping("/problema/{id}/status")
    public ResponseEntity<ProblemaResponseDTO> patchStatus(@PathVariable @Positive Long id, @RequestParam StatusProblema status) {
        ProblemaResponseDTO problema = service.patchStatus(id, status);
        return ResponseEntity.ok(problema);
    }

    @DeleteMapping("/problema/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
