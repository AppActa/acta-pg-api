package br.com.acta.controller;

import br.com.acta.common.config.security.UsuarioAutenticado;
import br.com.acta.dto.auth.MeResponseDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @GetMapping("/me")
    public ResponseEntity<MeResponseDTO> me(@AuthenticationPrincipal UsuarioAutenticado usuario) {
        return ResponseEntity.ok(MeResponseDTO.from(usuario));
    }
}
