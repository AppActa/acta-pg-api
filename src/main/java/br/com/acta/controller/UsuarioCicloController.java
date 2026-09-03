package br.com.acta.controller;

import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloRequestDTO;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloResponseDTO;
import br.com.acta.entity.enums.PapelCiclo;
import br.com.acta.service.UsuarioCicloService;
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
@RequestMapping(value = "/ciclo/{idCiclo}/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioCicloController {
    private final UsuarioCicloService service;

    @GetMapping
    public ResponseEntity<List<UsuarioCicloResponseDTO>> buscar(@PathVariable @Positive Long idCiclo){
        List<UsuarioCicloResponseDTO> usuario = service.buscarPorCiclo(idCiclo);
        return ResponseEntity.ok(usuario);
    }

    @PostMapping
    public ResponseEntity<UsuarioCicloResponseDTO> inserir(@PathVariable @Positive Long idCiclo, @RequestBody @Valid UsuarioCicloRequestDTO dto){
        UsuarioCicloResponseDTO usuario = service.inserir(dto, idCiclo);
        return ResponseEntity.status(201).body(usuario);
    }

    @PatchMapping("/{idUsuario}")
    public ResponseEntity<UsuarioCicloResponseDTO> patch(@PathVariable @Positive Long idCiclo, @PathVariable @Positive Long idUsuario, @RequestParam PapelCiclo papelCiclo) {
        UsuarioCicloResponseDTO usuario = service.patch(idUsuario, idCiclo, papelCiclo);
        return ResponseEntity.ok(usuario);
    }

    @PatchMapping("/substituir-responsavel")
    public ResponseEntity<List<UsuarioCicloResponseDTO>> substituirResponsavel(@PathVariable @Positive Long idCiclo, @RequestParam @Positive Long idUsuarioAntigo, @RequestParam @Positive Long idUsuarioNovo) {
        List<UsuarioCicloResponseDTO> usuarios = service.substituirResponsavel(idCiclo, idUsuarioAntigo, idUsuarioNovo);
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long idCiclo, @PathVariable @Positive Long idUsuario) {
        service.excluir(idUsuario, idCiclo);
        return ResponseEntity.noContent().build();
    }
}
