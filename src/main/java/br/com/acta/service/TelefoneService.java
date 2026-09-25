package br.com.acta.service;

import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.dto.core.contato.telefone.TelefoneColaboradorMapper;
import br.com.acta.dto.core.contato.telefone.TelefoneEmpresaMapper;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.contato.TelefoneColaborador;
import br.com.acta.entity.core.contato.TelefoneEmpresa;
import br.com.acta.repository.padrao.TelefoneColaboradorRepository;
import br.com.acta.repository.padrao.TelefoneEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TelefoneService {
    private final EmpresaService empresaService;
    private final ColaboradorService colaboradorService;
    private final TelefoneEmpresaRepository telefoneEmpresaRepo;
    private final TelefoneColaboradorRepository telefoneColaboradorRepo;
    private final TelefoneEmpresaMapper telefoneEmpresaMapper;
    private final TelefoneColaboradorMapper telefoneColaboradorMapper;
    private final AuthService authService;

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<TelefoneResponseDTO> buscarTelefonesEmpresa(Long idEmpresa) {
        Empresa empresa = empresaService.getEntity(idEmpresa);
        List<TelefoneEmpresa> telefones = telefoneEmpresaRepo.findByEmpresa_Id(empresa.getId());

        return telefoneEmpresaMapper.toResponseList(telefones);
    }

    @PreAuthorize("hasRole('ADMIN') and @authService.isUsuarioByIdEmpresa(#idEmpresa)")
    @Transactional
    public TelefoneResponseDTO inserirTelefoneEmpresa(Long idEmpresa, TelefoneRequestDTO dto) {
        authService.configurarUsuarioAtual();
        Empresa empresa = empresaService.getEntity(idEmpresa);

        if (telefoneEmpresaRepo.existsByEmpresaIdAndContato(idEmpresa, dto.numero())) throw new UniqueViolationException("Telefone");

        TelefoneEmpresa telefone = telefoneEmpresaMapper.toEntity(dto);
        telefone.setEmpresa(empresa);

        TelefoneEmpresa salvo = telefoneEmpresaRepo.save(telefone);
        return telefoneEmpresaMapper.toResponse(salvo);
    }

    @PreAuthorize("hasRole('ADMIN') and @authService.isUsuarioByIdEmpresa(#idEmpresa)")
    @Transactional
    public void excluirTelefoneEmpresa(Long idEmpresa, Long idTelefone) {
        authService.configurarUsuarioAtual();
        TelefoneEmpresa telefone = getTelefoneEmpresa(idEmpresa, idTelefone);
        telefoneEmpresaRepo.delete(telefone);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional(readOnly = true)
    public List<TelefoneResponseDTO> buscarTelefonesColaborador(Long idColaborador) {
        Colaborador colaborador = colaboradorService.getEntity(idColaborador);
        List<TelefoneColaborador> telefones = telefoneColaboradorRepo.findByColaborador_Id(colaborador.getId());

        return telefoneColaboradorMapper.toResponseList(telefones);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TelefoneResponseDTO inserirTelefoneColaborador(Long idColaborador, TelefoneRequestDTO dto) {
        authService.configurarUsuarioAtual();
        Colaborador colaborador = colaboradorService.getEntity(idColaborador);

        if (telefoneColaboradorRepo.existsByColaboradorIdAndContato(idColaborador, dto.numero())) throw new UniqueViolationException("Telefone");

        TelefoneColaborador telefone = telefoneColaboradorMapper.toEntity(dto);
        telefone.setColaborador(colaborador);

        TelefoneColaborador salvo = telefoneColaboradorRepo.save(telefone);
        return telefoneColaboradorMapper.toResponse(salvo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void excluirTelefoneColaborador(Long idColaborador, Long idTelefone) {
        authService.configurarUsuarioAtual();
        TelefoneColaborador telefone = getTelefoneColaborador(idColaborador, idTelefone);
        telefoneColaboradorRepo.delete(telefone);
    }

    private TelefoneEmpresa getTelefoneEmpresa(Long idEmpresa, Long idTelefone) {
        Empresa empresa = empresaService.getEntity(idEmpresa);
        return telefoneEmpresaRepo.findByEmpresaIdAndId(empresa.getId(), idTelefone)
                .orElseThrow(() -> new ModelNotFoundException("Telefone", idTelefone));
    }

    private TelefoneColaborador getTelefoneColaborador(Long idColaborador, Long idTelefone) {
        Colaborador colaborador = colaboradorService.getEntity(idColaborador);
        return telefoneColaboradorRepo.findByColaboradorIdAndId(colaborador.getId(), idTelefone)
                .orElseThrow(() -> new ModelNotFoundException("Telefone", idTelefone));
    }
}