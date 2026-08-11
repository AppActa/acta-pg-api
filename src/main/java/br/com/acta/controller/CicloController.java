package br.com.acta.controller;

import br.com.acta.dto.pdca.ciclo.CicloRequestDTO;
import br.com.acta.dto.pdca.ciclo.CicloResponseDTO;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.service.CicloService;
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
@RequestMapping(value = "/ciclo", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CicloController {//implements CicloOpenapi {
    private final CicloService service;

    @GetMapping
    public ResponseEntity<List<CicloResponseDTO>> buscar(@RequestParam(required = false) @Positive Long idEmpresa, @RequestParam(required = false) @Positive Long idGestor, @RequestParam(required = false) StatusCiclo status) {
        List<CicloResponseDTO> ciclos = service.buscarPorStatus(idEmpresa, idGestor, status);

        return ResponseEntity.ok(ciclos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CicloResponseDTO> buscar(@PathVariable @Positive Long id) {
        CicloResponseDTO ciclo = service.buscar(id);
        return ResponseEntity.ok(ciclo);
    }

    @PostMapping
    public ResponseEntity<CicloResponseDTO> inserir(@RequestBody @Valid CicloRequestDTO cicloRequest) {
        CicloResponseDTO ciclo = service.inserir(cicloRequest);
        return ResponseEntity.status(201).body(ciclo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CicloResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        CicloResponseDTO ciclo = service.patch(id, campos);
        return ResponseEntity.ok(ciclo);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CicloResponseDTO> patchStatus(@PathVariable @Positive Long id, @RequestParam StatusCiclo status) {
        CicloResponseDTO ciclo = service.patchStatus(id, status);
        return ResponseEntity.ok(ciclo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
