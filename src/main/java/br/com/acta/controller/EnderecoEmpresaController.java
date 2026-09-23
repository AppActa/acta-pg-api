package br.com.acta.controller;

import br.com.acta.common.config.swagger.openapi.EnderecoEmpresaOpenapi;
import br.com.acta.dto.core.empresa.endereco.EnderecoRequestDTO;
import br.com.acta.dto.core.empresa.endereco.EnderecoResponseDTO;
import br.com.acta.service.EnderecoEmpresaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/empresa/{idEmpresa}/endereco", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EnderecoEmpresaController implements EnderecoEmpresaOpenapi {
    private final EnderecoEmpresaService service;

    @GetMapping("/{idEndereco}")
    @Override
    public ResponseEntity<EnderecoResponseDTO> buscar(@PathVariable @Positive Long idEmpresa, @PathVariable @Positive Long idEndereco){
        EnderecoResponseDTO endereco = service.buscarEndereco(idEmpresa, idEndereco);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<EnderecoResponseDTO>> buscar(@PathVariable @Positive Long idEmpresa){
        List<EnderecoResponseDTO> enderecos = service.buscarEndereco(idEmpresa);
        return ResponseEntity.ok(enderecos);
    }

    @PostMapping
    @Override
    public ResponseEntity<EnderecoResponseDTO> inserir(@PathVariable @Positive Long idEmpresa, @Valid @RequestBody EnderecoRequestDTO dto){
        EnderecoResponseDTO endereco = service.inserirEndereco(idEmpresa, dto);
        return ResponseEntity.status(201).body(endereco);
    }

    @DeleteMapping("/{idEndereco}")
    @Override
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long idEmpresa, @PathVariable @Positive Long idEndereco){
        service.excluirEndereco(idEmpresa, idEndereco);
        return ResponseEntity.noContent().build();
    }
}
