package br.com.acta.controller;

import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioRequestDTO;
import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioResponseDTO;
import br.com.acta.service.EfeitoSecundarioService;
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
@RequestMapping(value = "/verificacao/{idResultado}/efeito-secundario", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EfeitoSecundarioController {
    private final EfeitoSecundarioService service;

    @GetMapping
    public ResponseEntity<List<EfeitoSecundarioResponseDTO>> buscarEfeitosSecundarios(@PathVariable @Positive Long idResultado) {
        List<EfeitoSecundarioResponseDTO> efeitoSecundario = service.buscar(idResultado);
        return ResponseEntity.ok(efeitoSecundario);
    }

    @PostMapping
    public ResponseEntity<EfeitoSecundarioResponseDTO> inserir(@PathVariable @Positive Long idResultado, @RequestBody @Valid EfeitoSecundarioRequestDTO dto) {
        EfeitoSecundarioResponseDTO efeitoSecundario = service.inserir(idResultado, dto);
        return ResponseEntity.status(201).body(efeitoSecundario);
    }

    @PatchMapping("{idEfeitoSecundario}")
    public ResponseEntity<EfeitoSecundarioResponseDTO> patch(@PathVariable @Positive Long idResultado, @PathVariable @Positive Long idEfeitoSecundario, @RequestBody Map<String, Object> campos) {
        EfeitoSecundarioResponseDTO efeitoSecundario = service.patch(idResultado, idEfeitoSecundario, campos);
        return ResponseEntity.ok(efeitoSecundario);
    }

    @DeleteMapping("/{idEfeitoSecundario}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long idResultado, @PathVariable @Positive Long idEfeitoSecundario) {
        service.excluir(idResultado, idEfeitoSecundario);
        return ResponseEntity.noContent().build();
    }
}
