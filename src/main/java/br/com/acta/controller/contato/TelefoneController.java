package br.com.acta.controller.contato;

import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.service.TelefoneService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class TelefoneController {
    private final TelefoneService service;

    @GetMapping("/empresa/{idEmpresa}/telefone")
    public ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneEmpresa(@PathVariable @Positive Long idEmpresa) {
        List<TelefoneResponseDTO> telefones = service.buscarTelefonesEmpresa(idEmpresa);
        return ResponseEntity.ok(telefones);
    }

    @PostMapping("/empresa/{idEmpresa}/telefone/")
    public ResponseEntity<TelefoneResponseDTO> inserirTelefoneEmpresa(@PathVariable @Positive Long idEmpresa, @RequestBody @Valid TelefoneRequestDTO dto) {
        TelefoneResponseDTO telefone = service.inserirTelefoneEmpresa(idEmpresa, dto);
        return ResponseEntity.status(201).body(telefone);
    }

    @DeleteMapping("/empresa/{idEmpresa}/telefone/{idTelefone}")
    public ResponseEntity<Void> excluirTelefoneEmpresa(@PathVariable @Positive Long idEmpresa, @PathVariable @Positive Long idTelefone) {
        service.excluirTelefoneEmpresa(idEmpresa, idTelefone);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/colaborador/{idColaborador}/telefone")
    public ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneColaborador(@PathVariable @Positive Long idColaborador) {
        List<TelefoneResponseDTO> telefones = service.buscarTelefonesColaborador(idColaborador);
        return ResponseEntity.ok(telefones);
    }

    @PostMapping("/colaborador/{idColaborador}/telefone/")
    public ResponseEntity<TelefoneResponseDTO> inserirTelefoneColaborador(@PathVariable @Positive Long idColaborador, @RequestBody @Valid TelefoneRequestDTO dto) {
        TelefoneResponseDTO telefone = service.inserirTelefoneColaborador(idColaborador, dto);
        return ResponseEntity.status(201).body(telefone);
    }

    @DeleteMapping("/colaborador/{idColaborador}/telefone/{idTelefone}")
    public ResponseEntity<Void> excluirTelefoneColaborador(@PathVariable @Positive Long idColaborador, @PathVariable @Positive Long idTelefone) {
        service.excluirTelefoneColaborador(idColaborador, idTelefone);
        return ResponseEntity.noContent().build();
    }
}
