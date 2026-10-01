package br.com.acta.controller;

import br.com.acta.common.config.swagger.openapi.EnderecoEmpresaOpenapi;
import br.com.acta.dto.core.empresa.endereco.EnderecoRequestDTO;
import br.com.acta.dto.core.empresa.endereco.EnderecoResponseDTO;
import br.com.acta.service.EnderecoEmpresaService;
import br.com.acta.service.AuthService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/empresa/endereco", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EnderecoEmpresaController implements EnderecoEmpresaOpenapi {
    private final EnderecoEmpresaService service;
    private final AuthService authService;

    @GetMapping("/{idEndereco}")
    @Override
    public ResponseEntity<EnderecoResponseDTO> buscar(@PathVariable @Positive Long idEndereco){
        EnderecoResponseDTO endereco = service.buscarEndereco(authService.atual().idEmpresa(), idEndereco);
        return ResponseEntity.ok(endereco);
    }

    @GetMapping
    @Override
    public ResponseEntity<List<EnderecoResponseDTO>> buscar(){
        List<EnderecoResponseDTO> enderecos = service.buscarEndereco(authService.atual().idEmpresa());
        return ResponseEntity.ok(enderecos);
    }

    @PostMapping
    @Override
    public ResponseEntity<EnderecoResponseDTO> inserir(@Valid @RequestBody EnderecoRequestDTO dto){
        EnderecoResponseDTO endereco = service.inserirEndereco(authService.atual().idEmpresa(), dto);
        return ResponseEntity.status(201).body(endereco);
    }

    @DeleteMapping("/{idEndereco}")
    @Override
    public ResponseEntity<Void> excluir(@PathVariable @Positive Long idEndereco){
        service.excluirEndereco(authService.atual().idEmpresa(), idEndereco);
        return ResponseEntity.noContent().build();
    }
}