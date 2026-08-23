package br.com.acta.controller;

import java.util.List;
import java.util.Map;

import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloResponseDTO;
import br.com.acta.service.UsuarioCicloService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioResponseDTO;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping(value = "/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService service;
    private final UsuarioCicloService usuarioCicloService;

    @GetMapping("/ciclos-usuario/{idUsuario}")
    public ResponseEntity<List<UsuarioCicloResponseDTO>> buscarCiclosUsuario(@PathVariable @Positive Long idUsuario) {
        List<UsuarioCicloResponseDTO> ciclos = usuarioCicloService.buscarPorUsuario(idUsuario);
        return ResponseEntity.ok(ciclos);
    }

    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<List<UsuarioResponseDTO>> buscar(@PathVariable @Positive Long idEmpresa, @RequestParam(required = false) TipoUsuario tipo) {
        List<UsuarioResponseDTO> usuarios = service.buscar(idEmpresa, tipo);
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable @Positive Long id){
        UsuarioResponseDTO usuario = service.buscar(id);
        return ResponseEntity.ok(usuario);
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
