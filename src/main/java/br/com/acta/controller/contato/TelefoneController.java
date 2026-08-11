package br.com.acta.controller.contato;

import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.service.ColaboradorService;
import br.com.acta.service.EmpresaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class TelefoneController {
    private final ColaboradorService colaboradorService;
    private final EmpresaService empresaService;

    @GetMapping("/empresa/{idEmpresa}/telefone")
    public ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneEmpresa(@PathVariable @Positive Long idEmpresa) {
        List<TelefoneResponseDTO> telefones = empresaService.buscarTelefones(idEmpresa);
        return ResponseEntity.ok(telefones);
    }

    @PostMapping("/empresa/{idEmpresa}/telefone/")
    public ResponseEntity<TelefoneResponseDTO> inserirTelefoneEmpresa(@PathVariable @Positive Long idEmpresa, @RequestBody @Valid TelefoneRequestDTO dto) {
        TelefoneResponseDTO telefone = empresaService.inserirTelefone(idEmpresa, dto);
        return ResponseEntity.status(201).body(telefone);
    }

    @DeleteMapping("/empresa/{idEmpresa}/telefone/{idTelefone}")
    public ResponseEntity<Void> excluirTelefoneEmpresa(@PathVariable @Positive Long idEmpresa, @PathVariable @Positive Long idTelefone) {
        empresaService.excluirTelefone(idEmpresa, idTelefone);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/colaborador/{idColaborador}/telefone")
    public ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneColaborador(@PathVariable @Positive Long idColaborador) {
        List<TelefoneResponseDTO> telefones = colaboradorService.buscarTelefones(idColaborador);
        return ResponseEntity.ok(telefones);
    }

    @PostMapping("/colaborador/{idColaborador}/telefone/")
    public ResponseEntity<TelefoneResponseDTO> inserirTelefoneColaborador(@PathVariable @Positive Long idColaborador, @RequestBody @Valid TelefoneRequestDTO dto) {
        TelefoneResponseDTO telefone = colaboradorService.inserirTelefone(idColaborador, dto);
        return ResponseEntity.status(201).body(telefone);
    }

    @DeleteMapping("/colaborador/{idColaborador}/telefone/{idTelefone}")
    public ResponseEntity<Void> excluirTelefoneColaborador(@PathVariable @Positive Long idColaborador, @PathVariable @Positive Long idTelefone) {
        colaboradorService.excluirTelefone(idColaborador, idTelefone);
        return ResponseEntity.noContent().build();
    }
}
