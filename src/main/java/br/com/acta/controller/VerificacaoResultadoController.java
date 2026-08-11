package br.com.acta.controller;

import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoRequestDTO;
import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoResponseDTO;
import br.com.acta.service.VerificacaoResultadoService;
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
public class VerificacaoResultadoController {
    private final VerificacaoResultadoService service;

    @GetMapping("/ciclos/{idCiclo}/verificacoes")
    public ResponseEntity< List<VerificacaoResultadoResponseDTO>> buscar(@PathVariable @Positive Long idCiclo) {
        List<VerificacaoResultadoResponseDTO> resultados = service.buscarVerificacoes(idCiclo);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/verificacoes/{id}")
    public ResponseEntity<VerificacaoResultadoResponseDTO> buscarPorId(@PathVariable @Positive Long id) {
        VerificacaoResultadoResponseDTO resultado = service.buscar(id);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping("/ciclos/{idCiclo}/verificacoes")
    public ResponseEntity<VerificacaoResultadoResponseDTO> inserir(@PathVariable @Positive Long idCiclo, @RequestParam @Positive Long idCriadoPor, @Valid @RequestBody VerificacaoResultadoRequestDTO dto){
        VerificacaoResultadoResponseDTO resultado = service.inserir(idCiclo, dto, idCriadoPor);
        return ResponseEntity.status(201).body(resultado);
    }

    @PatchMapping("/verificacoes/{id}")
    public ResponseEntity<VerificacaoResultadoResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        VerificacaoResultadoResponseDTO resultado = service.patch(id, campos);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/verificacoes/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
