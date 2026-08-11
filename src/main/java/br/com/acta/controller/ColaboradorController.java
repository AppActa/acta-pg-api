package br.com.acta.controller;

import br.com.acta.dto.core.colaborador.ColaboradorRequestDTO;
import br.com.acta.dto.core.colaborador.ColaboradorResponseDTO;
import br.com.acta.service.ColaboradorService;
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
@RequestMapping(value = "/colaborador", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ColaboradorController {
    private final ColaboradorService service;

    @GetMapping
    public ResponseEntity<List<ColaboradorResponseDTO>> buscar() {
        List<ColaboradorResponseDTO> colaboradores = service.buscar();
        return ResponseEntity.ok(colaboradores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColaboradorResponseDTO> buscar(@PathVariable @Positive Long id) {
        ColaboradorResponseDTO colaborador = service.buscar(id);
        return ResponseEntity.ok(colaborador);
    }

    @PostMapping
    public ResponseEntity<ColaboradorResponseDTO> inserir(@RequestBody @Valid ColaboradorRequestDTO dto) {
        ColaboradorResponseDTO colaborador = service.inserir(dto);
        return ResponseEntity.status(201).body(colaborador);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ColaboradorResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> dto) {
        ColaboradorResponseDTO colaborador = service.patch(id, dto);
        return ResponseEntity.ok(colaborador);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
