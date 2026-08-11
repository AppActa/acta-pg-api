package br.com.acta.controller;

import br.com.acta.dto.pdca.alerta_prazo.AlertaPrazoResponseDTO;
import br.com.acta.service.AlertaPrazoService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping(value = "/tarefa/{idTarefa}/alerta", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AlertaPrazoController {
    private final AlertaPrazoService service;

    @GetMapping
    public ResponseEntity<AlertaPrazoResponseDTO> buscar(@PathVariable @Positive Long idTarefa) {
        AlertaPrazoResponseDTO alerta = service.buscar(idTarefa);
        return ResponseEntity.ok(alerta);
    }

    @PostMapping
    public ResponseEntity<AlertaPrazoResponseDTO> inserir(@PathVariable @Positive Long idTarefa) {
        AlertaPrazoResponseDTO alerta = service.inserir(idTarefa);
        return ResponseEntity.status(201).body(alerta);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AlertaPrazoResponseDTO> marcarLido(@PathVariable @Positive Long idTarefa, @PathVariable @Positive Long id, @RequestParam @Positive Long idUsuario) {
        AlertaPrazoResponseDTO alerta = service.marcarLido(idTarefa, id, idUsuario);
        return ResponseEntity.ok(alerta);
    }
}
