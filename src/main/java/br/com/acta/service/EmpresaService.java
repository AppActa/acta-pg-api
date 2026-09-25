package br.com.acta.service;

import br.com.acta.common.handler.exception.ActiveEntityDeletionException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.handler.exception.RegexException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.common.utils.ConversorObject;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaResponseDTO;
import br.com.acta.dto.core.empresa.EmpresaMapper;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TamanhoEmpresa;
import br.com.acta.repository.padrao.EmailEmpresaRepository;
import br.com.acta.repository.padrao.EmpresaRepository;
import br.com.acta.repository.padrao.TelefoneEmpresaRepository;
import br.com.acta.service.base.BaseService;
import br.com.caelum.stella.validation.CNPJValidator;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class EmpresaService
extends BaseService<EmpresaRequestDTO, EmpresaResponseDTO, Empresa> {
    private final EmpresaRepository repo;
    private final EmpresaMapper mapper;
    private final EmailEmpresaRepository emailRepo;
    private final TelefoneEmpresaRepository telefoneRepo;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("cnpj", "nome", "tamanho", "setor", "status"),
            Set.of("nome", "tamanho", "setor")
    );
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    @Override
    public Empresa getEntity(Long id) {
        return repo.findByIdAndId(id, atual().idEmpresa())
                .orElseThrow(() -> new ModelNotFoundException("Empresa", id));
    }

    public EmpresaService(EmpresaRepository repo, EmpresaMapper mapper, EmailEmpresaRepository emailRepo, TelefoneEmpresaRepository telefoneRepo, AuthService authService) {
        super(repo, mapper, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.emailRepo = emailRepo;
        this.telefoneRepo = telefoneRepo;
    }

    @PreAuthorize("hasRole('ADMIN') and @authService.isUsuarioByIdEmpresa(#id)")
    @Override
    @Transactional
    public EmpresaResponseDTO patch(Long id, Map<String, Object> campos) {
        configurarUsuarioAtual();
        Validador.validarCampos(campos, patchConfig);
        Empresa empresa = getEntity(id);

        if (campos.containsKey("nome")) empresa.setNome((String) campos.get("nome"));
        if (campos.containsKey("setor")) empresa.setSetor((String) campos.get("setor"));
        if (campos.containsKey("tamanho")) {
            Object tamanhoObject = campos.get("tamanho");
            empresa.setTamanho(ConversorObject.toEnum(tamanhoObject, TamanhoEmpresa.class));
        }

        Empresa salvo = repo.save(empresa);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> buscar(TamanhoEmpresa tamanho){
        List<Empresa> empresas;

        if (tamanho == null) empresas = repo.findAllByStatus(StatusGeral.ATIVO);
        else empresas = repo.findByTamanhoAndStatus(tamanho, StatusGeral.ATIVO);

        return mapper.toResponseList(empresas);
    }

    @PreAuthorize("isAuthenticated() and @authService.isUsuarioByIdEmpresa(#id)")
    @Transactional(readOnly = true)
    @Override
    public EmpresaResponseDTO buscar(Long id) {
        Empresa empresa = getEntity(id);

        if (empresa.getStatus() == StatusGeral.INATIVO) throw new ModelNotFoundException("Empresa", id);
        return mapper.toResponse(empresa);
    }

    @PreAuthorize("hasAuthority('ROLE_SUPER_ADMIN')")
    @Transactional
    @Override
    public void excluir(Long id) {
        configurarUsuarioAtual();
        Empresa empresa = getEntity(id);

        empresa.getCiclos().forEach(ciclo -> {
            if (!ciclo.getStatus().equals(StatusCiclo.CANCELADO) && !ciclo.getStatus().equals(StatusCiclo.CONCLUIDO)){
                throw new ActiveEntityDeletionException("Empresa");
            }
        });

        empresa.setStatus(StatusGeral.INATIVO);

        repo.save(empresa);
    }

    @Override
    protected void antesInserir(Empresa empresa, EmpresaRequestDTO dto) {
        empresa.setStatus(StatusGeral.ATIVO);

        // validando cnpj
        if (!cnpjValidator.isEligible(dto.cnpj())) throw new RegexException("CNPJ");
        if (repo.existsByCnpj(dto.cnpj())) throw new UniqueViolationException("CNPJ");

        for (EmailRequestDTO email : dto.emails()) {
            if (emailRepo.existsByContatoIgnoreCase(email.email())) throw new UniqueViolationException("E-mail");
        }

        for (TelefoneRequestDTO telefone : dto.telefones()) {
            if (telefoneRepo.existsByContatoIgnoreCase(telefone.numero())) throw new UniqueViolationException("Telefone");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Override
    @Transactional
    public EmpresaResponseDTO inserir(EmpresaRequestDTO dto) {
        return super.inserir(dto);
    }
}