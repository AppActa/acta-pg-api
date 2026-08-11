package br.com.acta.controller.contato;

import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import br.com.acta.service.ColaboradorService;
import br.com.acta.service.EmpresaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class EmailController {
    private final ColaboradorService colaboradorService;
    private final EmpresaService empresaService;

    @GetMapping("/empresa/{idEmpresa}/email")
    public ResponseEntity<List<EmailResponseDTO>> buscarEmailEmpresa(@PathVariable @Positive Long idEmpresa) {
        List<EmailResponseDTO> emails = empresaService.buscarEmails(idEmpresa);
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/empresa/{idEmpresa}/email/")
    public ResponseEntity<EmailResponseDTO> inserirEmailEmpresa(@PathVariable @Positive Long idEmpresa, @RequestBody @Valid EmailRequestDTO dto) {
        EmailResponseDTO email = empresaService.inserirEmail(idEmpresa, dto);
        return ResponseEntity.status(201).body(email);
    }

    @DeleteMapping("/empresa/{idEmpresa}/email/{idEmail}")
    public ResponseEntity<Void> excluirEmailEmpresa(@PathVariable @Positive Long idEmpresa, @PathVariable @Positive Long idEmail) {
        empresaService.excluirEmail(idEmpresa, idEmail);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/colaborador/{idColaborador}/email")
    public ResponseEntity<List<EmailResponseDTO>> buscarEmailColaborador(@PathVariable @Positive Long idColaborador) {
        List<EmailResponseDTO> emails = colaboradorService.buscarEmails(idColaborador);
        return ResponseEntity.ok(emails);
    }

    @PostMapping("/colaborador/{idColaborador}/email/")
    public ResponseEntity<EmailResponseDTO> inserirEmailColaborador(@PathVariable @Positive Long idColaborador, @RequestBody @Valid EmailRequestDTO dto) {
        EmailResponseDTO email = colaboradorService.inserirEmail(idColaborador, dto);
        return ResponseEntity.status(201).body(email);
    }

    @DeleteMapping("/colaborador/{idColaborador}/email/{idEmail}")
    public ResponseEntity<Void> excluirEmailColaborador(@PathVariable @Positive Long idColaborador, @PathVariable @Positive Long idEmail) {
        colaboradorService.excluirEmail(idColaborador, idEmail);
        return ResponseEntity.noContent().build();
    }
}
