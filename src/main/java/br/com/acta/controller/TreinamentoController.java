package br.com.acta.controller;

import br.com.acta.dto.pdca.treinamento.TreinamentoRequestDTO;
import br.com.acta.dto.pdca.treinamento.TreinamentoResponseDTO;
import br.com.acta.service.TreinamentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TreinamentoController {
    private final TreinamentoService service;

    @GetMapping("/ciclo/{idCiclo}/treinamento")
    public ResponseEntity<List<TreinamentoResponseDTO>> buscarTreinamentos(@PathVariable @Positive Long idCiclo) {
        List<TreinamentoResponseDTO> treinamentos = service.buscarTreinamentos(idCiclo);
        return ResponseEntity.ok(treinamentos);
    }

    @GetMapping("/treinamento/{id}")
    public ResponseEntity<TreinamentoResponseDTO> buscar(@PathVariable @Positive Long id){
        TreinamentoResponseDTO treinamento = service.buscar(id);
        return ResponseEntity.ok(treinamento);
    }

    @PostMapping("/ciclo/{idCiclo}/treinamento")
    public ResponseEntity<TreinamentoResponseDTO> inserir(@PathVariable @Positive Long idCiclo, @RequestBody @Valid TreinamentoRequestDTO dto) {
        TreinamentoResponseDTO treinamento = service.inserir(idCiclo, dto);
        return ResponseEntity.status(201).body(treinamento);
    }

    @PatchMapping("/treinamento/{id}")
    public ResponseEntity<TreinamentoResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        TreinamentoResponseDTO treinamento = service.patch(id, campos);
        return ResponseEntity.ok(treinamento);
    }

    @DeleteMapping("/treinamento/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
