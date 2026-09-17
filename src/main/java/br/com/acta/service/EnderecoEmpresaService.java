package br.com.acta.service;

import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.dto.core.empresa.endereco.EnderecoMapper;
import br.com.acta.dto.core.empresa.endereco.EnderecoRequestDTO;
import br.com.acta.dto.core.empresa.endereco.EnderecoResponseDTO;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Endereco;
import br.com.acta.repository.padrao.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoEmpresaService {
    private final EmpresaService empresaService;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoRepository enderecoRepo;
    private final AuthService authService;

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarEndereco(Long idEmpresa, Long idEndereco) {
        Endereco endereco = getEndereco(idEmpresa, idEndereco);
        return enderecoMapper.toResponse(endereco);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> buscarEndereco(Long idEmpresa) {
        Empresa empresa = empresaService.getEntity(idEmpresa);
        List<Endereco> enderecos = empresa.getEnderecos();
        return enderecoMapper.toResponseList(enderecos);
    }

    @PreAuthorize("hasRole('ADMIN') and authService.isUsuarioByIdEmpresa(#idEmpresa)")
    @Transactional
    public EnderecoResponseDTO inserirEndereco(Long idEmpresa, EnderecoRequestDTO dto) {
        authService.configurarUsuarioAtual();
        Empresa empresa = empresaService.getEntity(idEmpresa);
        Endereco endereco = enderecoMapper.toEntity(dto);

        endereco.setEmpresa(empresa);
        Endereco salvo = enderecoRepo.save(endereco);
        return enderecoMapper.toResponse(salvo);
    }

    @PreAuthorize("hasRole('ADMIN') and authService.isUsuarioByIdEmpresa(#idEmpresa)")
    @Transactional
    public void excluirEndereco(Long idEmpresa, Long idEndereco) {
        authService.configurarUsuarioAtual();
        Endereco endereco = getEndereco(idEmpresa, idEndereco);
        enderecoRepo.delete(endereco);
    }

    private Endereco getEndereco(Long idEmpresa, Long idEndereco) {
        Empresa empresa = empresaService.getEntity(idEmpresa);
        return enderecoRepo.findByEmpresaAndId(empresa, idEndereco)
                .orElseThrow(() -> new ModelNotFoundException("Endereco", idEndereco));
    }
}
