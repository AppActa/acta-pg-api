package br.com.acta.service;

import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.dto.core.contato.email.EmailColaboradorMapper;
import br.com.acta.dto.core.contato.email.EmailEmpresaMapper;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.contato.EmailColaborador;
import br.com.acta.entity.core.contato.EmailEmpresa;
import br.com.acta.repository.padrao.EmailColaboradorRepository;
import br.com.acta.repository.padrao.EmailEmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmpresaService empresaService;
    private final ColaboradorService colaboradorService;
    private final EmailEmpresaRepository emailEmpresaRepo;
    private final EmailColaboradorRepository emailColaboradorRepo;
    private final EmailEmpresaMapper emailEmpresaMapper;
    private final EmailColaboradorMapper emailColaboradorMapper;
    private final AuthService authService;

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<EmailResponseDTO> buscarEmailsEmpresa(Long idEmpresa) {
        Empresa empresa = empresaService.getEntity(idEmpresa);
        List<EmailEmpresa> emails = emailEmpresaRepo.findByEmpresa_Id(empresa.getId());

        return emailEmpresaMapper.toResponseList(emails);
    }

    @PreAuthorize("hasRole('ADMIN') and authService.isUsuarioByIdEmpresa(#idEmpresa)")
    @Transactional
    public EmailResponseDTO inserirEmailEmpresa(Long idEmpresa, EmailRequestDTO dto) {
        authService.configurarUsuarioAtual();
        Empresa empresa = empresaService.getEntity(idEmpresa);

        if (emailEmpresaRepo.existsByEmpresaIdAndContatoIgnoreCase(idEmpresa, dto.email())) throw new UniqueViolationException("E-mail");

        EmailEmpresa email = emailEmpresaMapper.toEntity(dto);
        email.setEmpresa(empresa);

        EmailEmpresa salvo = emailEmpresaRepo.save(email);
        return emailEmpresaMapper.toResponse(salvo);
    }

    @PreAuthorize("hasRole('ADMIN') and authService.isUsuarioByIdEmpresa(#idEmpresa)")
    @Transactional
    public void excluirEmailEmpresa(Long idEmpresa, Long idEmail) {
        authService.configurarUsuarioAtual();
        EmailEmpresa email = getEmailEmpresa(idEmpresa, idEmail);
        emailEmpresaRepo.delete(email);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional(readOnly = true)
    public List<EmailResponseDTO> buscarEmailsColaborador(Long idColaborador) {
        Colaborador colaborador = colaboradorService.getEntity(idColaborador);
        List<EmailColaborador> emails = emailColaboradorRepo.findByColaborador_Id(colaborador.getId());

        return emailColaboradorMapper.toResponseList(emails);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public EmailResponseDTO inserirEmailColaborador(Long idColaborador, EmailRequestDTO dto) {
        authService.configurarUsuarioAtual();
        Colaborador colaborador = colaboradorService.getEntity(idColaborador);

        if (emailColaboradorRepo.existsByColaboradorIdAndContatoIgnoreCase(idColaborador, dto.email())) throw new UniqueViolationException("E-mail");

        EmailColaborador email = emailColaboradorMapper.toEntity(dto);
        email.setColaborador(colaborador);

        EmailColaborador salvo = emailColaboradorRepo.save(email);
        return emailColaboradorMapper.toResponse(salvo);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void excluirEmailColaborador(Long idColaborador, Long idEmail) {
        authService.configurarUsuarioAtual();
        EmailColaborador email = getEmailColaborador(idColaborador, idEmail);
        emailColaboradorRepo.delete(email);
    }

    private EmailEmpresa getEmailEmpresa(Long idEmpresa, Long idEmail) {
        Empresa empresa = empresaService.getEntity(idEmpresa);
        return emailEmpresaRepo.findByEmpresaIdAndId(empresa.getId(), idEmail)
                .orElseThrow(() -> new ModelNotFoundException("Email", idEmail));
    }

    private EmailColaborador getEmailColaborador(Long idColaborador, Long idEmail) {
        Colaborador colaborador = colaboradorService.getEntity(idColaborador);
        return emailColaboradorRepo.findByColaboradorIdAndId(colaborador.getId(), idEmail)
                .orElseThrow(() -> new ModelNotFoundException("Email", idEmail));
    }
}
