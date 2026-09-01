package br.com.acta.controller;

import br.com.acta.common.config.security.FirebaseAuthFilter.FirebaseIdentity;
import br.com.acta.common.config.security.UsuarioAutenticado;
import br.com.acta.dto.auth.MeResponseDTO;
import br.com.acta.dto.mapper.auth.AuthMapper;
import br.com.acta.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;
    private final AuthMapper mapper;

    @GetMapping("/me")
    public ResponseEntity<MeResponseDTO> me(@AuthenticationPrincipal UsuarioAutenticado usuario) {
        return ResponseEntity.ok(mapper.toMeResponse(usuario));
    }

    @PostMapping("/auth/ativar")
    public ResponseEntity<MeResponseDTO> ativar(@AuthenticationPrincipal FirebaseIdentity identity) {
        return ResponseEntity.ok(service.ativar(identity));
    }
}
