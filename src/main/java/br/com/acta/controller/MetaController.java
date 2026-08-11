package br.com.acta.controller;

import br.com.acta.dto.core.usuario.UsuarioSummaryResponseDTO;
import br.com.acta.dto.pdca.meta.MetaRequestDTO;
import br.com.acta.dto.pdca.meta.MetaResponseDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusMeta;
import br.com.acta.service.MetaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
public class MetaController {
    private final MetaService service;

    @GetMapping("/ciclos/{idCiclo}/meta")
    public ResponseEntity<List<MetaResponseDTO>> buscar(@PathVariable @Positive Long idCiclo, @RequestParam(required = false) StatusMeta status, @RequestParam(required = false) Prioridade prioridade) {
        List<MetaResponseDTO> metas = service.buscar(idCiclo, status, prioridade);
        return ResponseEntity.ok(metas);
    }

    @GetMapping("/meta/{id}")
    public ResponseEntity<MetaResponseDTO> buscar(@PathVariable @Positive Long id) {
        MetaResponseDTO meta = service.buscar(id);
        return ResponseEntity.ok(meta);
    }

    @PostMapping("/plano-acao/{idPlanoAcao}/meta")
    public ResponseEntity<MetaResponseDTO> inserir(@PathVariable @Positive Long idPlanoAcao, @Valid @RequestBody MetaRequestDTO dto) {
        MetaResponseDTO meta = service.inserir(idPlanoAcao, dto);
        return ResponseEntity.status(201).body(meta);
    }

    @PatchMapping("/meta/{id}")
    public ResponseEntity<MetaResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> dto) {
        MetaResponseDTO meta = service.patch(id, dto);
        return ResponseEntity.ok(meta);
    }

    @PatchMapping("/meta/{id}/status")
    public ResponseEntity<MetaResponseDTO> patchStatus(@PathVariable @Positive Long id, StatusMeta status) {
        MetaResponseDTO meta = service.patchStatus(id, status);
        return ResponseEntity.ok(meta);
    }

    @DeleteMapping("/meta/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/meta/{id}/responsaveis")
    public ResponseEntity<List<UsuarioSummaryResponseDTO>> buscarResponsaveis(@PathVariable @Positive Long id) {
        List<UsuarioSummaryResponseDTO> responsaveis = service.buscarResponsaveis(id);
        return ResponseEntity.ok(responsaveis);
    }

    @PostMapping("/meta/{id}/responsaveis")
    public ResponseEntity<List<UsuarioSummaryResponseDTO>> adicionarResponsavel(@RequestParam @NotEmpty List<@Positive Long> idsResponsaveis, @PathVariable Long id) {
        List<UsuarioSummaryResponseDTO> responsaveis = service.inserirResponsaveis(id, idsResponsaveis);
        return ResponseEntity.status(201).body(responsaveis);
    }

    @DeleteMapping("/meta/{id}/responsaveis")
    public ResponseEntity<Void> excluirResponsaveis(@PathVariable Long id, @RequestParam List<Long> idsResponsaveis) {
        service.excluirResponsaveis(id, idsResponsaveis);
        return ResponseEntity.noContent().build();
    }
}
