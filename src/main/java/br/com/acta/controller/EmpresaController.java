package br.com.acta.controller;

import br.com.acta.dto.core.empresa.EmpresaRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaResponseDTO;
import br.com.acta.entity.enums.TamanhoEmpresa;
import br.com.acta.service.EmpresaService;
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
@RequestMapping(value = "/empresa", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmpresaController {
    private final EmpresaService service;

    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> buscar(@RequestParam(required = false) TamanhoEmpresa tamanho) {
        List<EmpresaResponseDTO> empresas = service.buscar(tamanho);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscar(@PathVariable @Positive Long id) {
        EmpresaResponseDTO empresa = service.buscar(id);
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> inserir(@RequestBody @Valid EmpresaRequestDTO dto) {
        EmpresaResponseDTO empresa = service.inserir(dto);
        return ResponseEntity.status(201).body(empresa);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        EmpresaResponseDTO empresa = service.patch(id, campos);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
