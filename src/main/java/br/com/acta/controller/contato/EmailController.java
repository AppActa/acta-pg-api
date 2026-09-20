package br.com.acta.controller.contato;

import br.com.acta.common.config.swagger.openapi.EmailOpenapi;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import br.com.acta.service.EmailService;
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
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EmailController implements EmailOpenapi {
    private final EmailService service;

    @GetMapping("/empresa/{idEmpresa}/email")
    @Override
    public ResponseEntity<List<EmailResponseDTO>> buscarEmailEmpresa(@PathVariable @Positive Long idEmpresa) {
        List<EmailResponseDTO> emails = service.buscarEmailsEmpresa(idEmpresa);
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/empresa/{idEmpresa}/email/")
    @Override
    public ResponseEntity<EmailResponseDTO> inserirEmailEmpresa(@PathVariable @Positive Long idEmpresa, @RequestBody @Valid EmailRequestDTO dto) {
        EmailResponseDTO email = service.inserirEmailEmpresa(idEmpresa, dto);
        return ResponseEntity.status(201).body(email);
    }

    @DeleteMapping("/empresa/{idEmpresa}/email/{idEmail}")
    @Override
    public ResponseEntity<Void> excluirEmailEmpresa(@PathVariable @Positive Long idEmpresa, @PathVariable @Positive Long idEmail) {
        service.excluirEmailEmpresa(idEmpresa, idEmail);
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
