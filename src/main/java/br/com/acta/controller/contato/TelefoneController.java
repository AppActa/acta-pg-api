package br.com.acta.controller.contato;

import br.com.acta.common.config.swagger.openapi.TelefoneOpenapi;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.service.TelefoneService;
import br.com.acta.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class TelefoneController implements TelefoneOpenapi {
    private final TelefoneService service;
    private final AuthService authService;

    @GetMapping("/empresa/telefone")
    @Override
    public ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneEmpresa() {
        List<TelefoneResponseDTO> telefones = service.buscarTelefonesEmpresa(authService.atual().idEmpresa());
        return ResponseEntity.ok(telefones);
    }

    @PostMapping("/empresa/telefone/")
    @Override
    public ResponseEntity<TelefoneResponseDTO> inserirTelefoneEmpresa(@RequestBody @Valid TelefoneRequestDTO dto) {
        TelefoneResponseDTO telefone = service.inserirTelefoneEmpresa(authService.atual().idEmpresa(), dto);
        return ResponseEntity.status(201).body(telefone);
    }

    @DeleteMapping("/empresa/telefone/{idTelefone}")
    @Override
    public ResponseEntity<Void> excluirTelefoneEmpresa(@PathVariable @Positive Long idTelefone) {
        service.excluirTelefoneEmpresa(authService.atual().idEmpresa(), idTelefone);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/colaborador/{idColaborador}/telefone")
    @Override
    public ResponseEntity<List<TelefoneResponseDTO>> buscarTelefoneColaborador(@PathVariable @Positive Long idColaborador) {
        List<TelefoneResponseDTO> telefones = service.buscarTelefonesColaborador(idColaborador);
        return ResponseEntity.ok(telefones);
    }

    @PostMapping("/colaborador/{idColaborador}/telefone/")
    @Override
    public ResponseEntity<TelefoneResponseDTO> inserirTelefoneColaborador(@PathVariable @Positive Long idColaborador, @RequestBody @Valid TelefoneRequestDTO dto) {
        TelefoneResponseDTO telefone = service.inserirTelefoneColaborador(idColaborador, dto);
        return ResponseEntity.status(201).body(telefone);
    }

    @DeleteMapping("/colaborador/{idColaborador}/telefone/{idTelefone}")
    @Override
    public ResponseEntity<Void> excluirTelefoneColaborador(@PathVariable @Positive Long idColaborador, @PathVariable @Positive Long idTelefone) {
        service.excluirTelefoneColaborador(idColaborador, idTelefone);
        return ResponseEntity.noContent().build();
    }
}