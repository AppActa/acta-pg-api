package br.com.acta.controller.contato;

import br.com.acta.common.config.swagger.openapi.EmailOpenapi;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import br.com.acta.service.EmailService;
import br.com.acta.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmailController implements EmailOpenapi {
    private final EmailService service;
    private final AuthService authService;

    @GetMapping("/empresa/email")
    @Override
    public ResponseEntity<List<EmailResponseDTO>> buscarEmailEmpresa() {
        List<EmailResponseDTO> emails = service.buscarEmailsEmpresa(authService.atual().idEmpresa());
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/empresa/email/")
    @Override
    public ResponseEntity<EmailResponseDTO> inserirEmailEmpresa(@RequestBody @Valid EmailRequestDTO dto) {
        EmailResponseDTO email = service.inserirEmailEmpresa(authService.atual().idEmpresa(), dto);
        return ResponseEntity.status(201).body(email);
    }

    @DeleteMapping("/empresa/email/{idEmail}")
    @Override
    public ResponseEntity<Void> excluirEmailEmpresa(@PathVariable @Positive Long idEmail) {
        service.excluirEmailEmpresa(authService.atual().idEmpresa(), idEmail);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/colaborador/{idColaborador}/email")
    @Override
    public ResponseEntity<List<EmailResponseDTO>> buscarEmailColaborador(@PathVariable @Positive Long idColaborador) {
        List<EmailResponseDTO> emails = service.buscarEmailsColaborador(idColaborador);
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/colaborador/{idColaborador}/email/")
    @Override
    public ResponseEntity<EmailResponseDTO> inserirEmailColaborador(@PathVariable @Positive Long idColaborador, @RequestBody @Valid EmailRequestDTO dto) {
        EmailResponseDTO email = service.inserirEmailColaborador(idColaborador, dto);
        return ResponseEntity.status(201).body(email);
    }

    @DeleteMapping("/colaborador/{idColaborador}/email/{idEmail}")
    @Override
    public ResponseEntity<Void> excluirEmailColaborador(@PathVariable @Positive Long idColaborador, @PathVariable @Positive Long idEmail) {
        service.excluirEmailColaborador(idColaborador, idEmail);
        return ResponseEntity.noContent().build();
    }
}