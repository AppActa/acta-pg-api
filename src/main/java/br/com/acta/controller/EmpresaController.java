package br.com.acta.controller;

import br.com.acta.common.config.swagger.openapi.EmpresaOpenapi;
import br.com.acta.dto.core.empresa.EmpresaRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaResponseDTO;
import br.com.acta.entity.enums.TamanhoEmpresa;
import br.com.acta.service.EmpresaService;
import br.com.acta.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api/v1/empresa", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmpresaController implements EmpresaOpenapi {
    private final EmpresaService service;
    private final AuthService authService;

    @GetMapping
    @Override
    public ResponseEntity<List<EmpresaResponseDTO>> buscar(@RequestParam(required = false) TamanhoEmpresa tamanho) {
        List<EmpresaResponseDTO> empresas = service.buscar(tamanho);
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/atual")
    @Override
    public ResponseEntity<EmpresaResponseDTO> buscar() {
        EmpresaResponseDTO empresa = service.buscar(authService.atual().idEmpresa());
        return ResponseEntity.ok(empresa);
    }

    @PostMapping
    @Override
    public ResponseEntity<EmpresaResponseDTO> inserir(@RequestBody @Valid EmpresaRequestDTO dto) {
        EmpresaResponseDTO empresa = service.inserir(dto);
        return ResponseEntity.status(201).body(empresa);
    }

    @PatchMapping()
    @Override
    public ResponseEntity<EmpresaResponseDTO> patch(@RequestBody Map<String, Object> campos) {
        EmpresaResponseDTO empresa = service.patch(authService.atual().idEmpresa(), campos);
        return ResponseEntity.ok(empresa);
    }

    @DeleteMapping()
    @Override
    public ResponseEntity<Void> excluir() {
        service.excluir(authService.atual().idEmpresa());
        return ResponseEntity.noContent().build();
    }
}