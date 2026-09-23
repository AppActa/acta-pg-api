package br.com.acta.controller;

import br.com.acta.common.config.swagger.openapi.CausaRaizOpenapi;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizRequestDTO;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizResponseDTO;
import br.com.acta.service.CausaRaizService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CausaRaizController implements CausaRaizOpenapi {
    private final CausaRaizService service;

    @GetMapping("/ciclos/{idCiclo}/causas-raiz")
    @Override
    public ResponseEntity<List<CausaRaizResponseDTO>> buscar(@PathVariable @Positive Long idCiclo, @RequestParam(required = false) Boolean aceita, @RequestParam(required = false) Boolean principal, @RequestParam(required = false) Long idProblema) {
        List<CausaRaizResponseDTO> ciclos = service.buscar(idCiclo, idProblema, aceita, principal);
        return ResponseEntity.ok(ciclos);
    }

    @GetMapping("/causas-raiz/{id}")
    @Override
    public ResponseEntity<CausaRaizResponseDTO> buscar(@PathVariable @Positive Long id) {
        CausaRaizResponseDTO ciclo = service.buscar(id);
        return ResponseEntity.ok(ciclo);
    }

    @PostMapping("/ciclos/{idCiclo}/causas-raiz")
    @Override
    public ResponseEntity<CausaRaizResponseDTO> inserir(@PathVariable @Positive Long idCiclo, @Valid @RequestBody CausaRaizRequestDTO dto) {
        CausaRaizResponseDTO ciclo = service.inserir(dto, idCiclo);
        return ResponseEntity.status(201).body(ciclo);
    }

    @PatchMapping("/causas-raiz/{id}")
    @Override
    public ResponseEntity<CausaRaizResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        CausaRaizResponseDTO ciclo = service.patch(id, campos);
        return ResponseEntity.ok(ciclo);
    }

    @PatchMapping("/causas-raiz/{id}/validar")
    @Override
    public ResponseEntity<CausaRaizResponseDTO> validar(@PathVariable @Positive Long id, @RequestParam @Positive Long idUsuario, @RequestParam Boolean aceita) {
        CausaRaizResponseDTO ciclo = service.validar(id, idUsuario, aceita);
        return ResponseEntity.ok(ciclo);
    }

    @DeleteMapping("/causas-raiz/{id}")
    @Override
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
