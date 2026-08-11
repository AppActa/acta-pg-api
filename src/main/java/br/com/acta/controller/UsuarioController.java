package br.com.acta.controller;

import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioResponseDTO;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.service.UsuarioService;
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
@RequestMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;

    @GetMapping("/{idEmpresa}")
    public ResponseEntity<List<UsuarioResponseDTO>> buscar(@PathVariable @Positive Long idEmpresa, @RequestParam(required = false) TipoUsuario tipo) {
        List<UsuarioResponseDTO> usuarios = service.buscar(idEmpresa, tipo);
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> inserir(@RequestBody @Valid UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuario = service.inserir(dto);
        return ResponseEntity.status(201).body(usuario);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> patch(@PathVariable @Positive Long id, @RequestBody Map<String, Object> campos) {
        UsuarioResponseDTO usuario = service.patch(id, campos);
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
