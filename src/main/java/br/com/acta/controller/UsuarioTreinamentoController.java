package br.com.acta.controller;

import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoRequestDTO;
import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoResponseDTO;
import br.com.acta.entity.enums.StatusTreinamento;
import br.com.acta.service.UsuarioTreinamentoService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/treinamento/{id}/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioTreinamentoController {
    private final UsuarioTreinamentoService service;

    @GetMapping
    public ResponseEntity<List<UsuarioTreinamentoResponseDTO>> buscar(@PathVariable @Positive Long id) {
        List<UsuarioTreinamentoResponseDTO> usuarios = service.buscar(id);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioTreinamentoResponseDTO> inserir(@PathVariable @Positive Long id, UsuarioTreinamentoRequestDTO dto){
        UsuarioTreinamentoResponseDTO usuario = service.inserir(id, dto);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/{idUsuario}")
    public ResponseEntity<UsuarioTreinamentoResponseDTO> patchStatus(@PathVariable @Positive Long id, @PathVariable @Positive Long idUsuario, @RequestParam StatusTreinamento status){
        UsuarioTreinamentoResponseDTO usuario = service.patchStatus(id, idUsuario, status);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id, @PathVariable @Positive Long idUsuario){
        service.excluir(id, idUsuario);
        return ResponseEntity.noContent().build();
    }
}
