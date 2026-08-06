package br.com.acta.service;

import br.com.acta.common.handler.exception.ActiveEntityDeletionException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.handler.exception.RegexException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.dto.core.empresa.EmpresaRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaResponseDTO;
import br.com.acta.dto.mapper.core.EmpresaMapper;
import br.com.acta.dto.mapper.core.contato.EmailEmpresaMapper;
import br.com.acta.dto.mapper.core.contato.TelefoneEmpresaMapper;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.contato.EmailEmpresa;
import br.com.acta.entity.core.contato.TelefoneEmpresa;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TamanhoEmpresa;
import br.com.acta.repository.padrao.EmailEmpresaRepository;
import br.com.acta.repository.padrao.EmpresaRepository;
import br.com.acta.repository.padrao.TelefoneEmpresaRepository;
import br.com.acta.service.base.BaseService;
import br.com.caelum.stella.validation.CNPJValidator;
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
    private final EmailEmpresaMapper emailMapper;
    private final EmailEmpresaRepository emailRepo;
    private final TelefoneEmpresaMapper telefoneMapper;
    private final TelefoneEmpresaRepository telefoneRepo;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("cnpj", "nome", "tamanho", "setor", "status"),
            Set.of("nome", "tamanho", "setor", "status")
    );
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    public EmpresaService(EmpresaRepository repo, EmpresaMapper mapper, EmailEmpresaMapper emailMapper, EmailEmpresaRepository emailRepo, TelefoneEmpresaMapper telefoneMapper, TelefoneEmpresaRepository telefoneRepo) {
        super(repo, mapper, Empresa.class);
        this.repo = repo;
        this.mapper = mapper;
        this.emailMapper = emailMapper;
        this.emailRepo = emailRepo;
        this.telefoneMapper = telefoneMapper;
        this.telefoneRepo = telefoneRepo;
    }

    @Override
    @Transactional
    public EmpresaResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Empresa empresa = getEntity(id);

        if (campos.containsKey("nome")) empresa.setNome((String) campos.get("nome"));
        if (campos.containsKey("tamanho")) empresa.setTamanho((TamanhoEmpresa) campos.get("tamanho"));
        if (campos.containsKey("setor")) empresa.setSetor((String) campos.get("setor"));
        if (campos.containsKey("status")) empresa.setStatus((StatusGeral) campos.get("status"));

        Empresa salvo = repo.save(empresa);
        return mapper.toResponse(salvo);
    }

    @Override
    public List<EmpresaResponseDTO> buscar() {
        List<Empresa> empresas = repo.findAllByStatus(StatusGeral.ATIVO);

        return mapper.toResponseList(empresas);
    }

    public List<EmpresaResponseDTO> buscar(TamanhoEmpresa tamanho){
        List<Empresa> empresas = repo.findByTamanhoAndStatus(tamanho, StatusGeral.ATIVO);

        return mapper.toResponseList(empresas);
    }

    @Override
    public EmpresaResponseDTO buscar(Long id) {
        Empresa empresa = getEntity(id);

        if (empresa.getStatus() == StatusGeral.INATIVO) throw new ModelNotFoundException("Empresa", id);
        return mapper.toResponse(empresa);
    }

    @Override
    public void excluir(Long id) {
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
    }

    public List<EmailResponseDTO> buscarEmails(Long idEmpresa){
        Empresa empresa = getEntity(idEmpresa);
        List<EmailEmpresa> email = emailRepo.findByEmpresa_Id(empresa.getId());

        return emailMapper.toResponseList(email);
    }

    public EmailResponseDTO inserirEmail(Long idEmpresa, EmailRequestDTO dto){
        Empresa empresa = getEntity(idEmpresa);
        EmailEmpresa email = emailMapper.toEntity(dto);
        email.setEmpresa(empresa);

        EmailEmpresa salvo = emailRepo.save(email);
        return emailMapper.toResponse(salvo);
    }

    public void excluirEmail(Long idEmpresa, Long idEmail){
        EmailEmpresa email = emailRepo.findByEmpresaIdAndId(idEmpresa, idEmail);
        emailRepo.delete(email);
    }

    public List<TelefoneResponseDTO> buscarTelefones(Long idEmpresa){
        Empresa empresa = getEntity(idEmpresa);
        List<TelefoneEmpresa> telefone = telefoneRepo.findByEmpresa_Id(empresa.getId());

        return telefoneMapper.toResponseList(telefone);
    }

    public TelefoneResponseDTO inserirTelefone(Long idEmpresa, TelefoneRequestDTO dto){
        Empresa empresa = getEntity(idEmpresa);
        TelefoneEmpresa telefone = telefoneMapper.toEntity(dto);
        telefone.setEmpresa(empresa);

        TelefoneEmpresa salvo = telefoneRepo.save(telefone);
        return telefoneMapper.toResponse(salvo);
    }

    public void excluirTelefone(Long idEmpresa, Long idTelefone){
        Empresa empresa = getEntity(idEmpresa);
        TelefoneEmpresa telefone = telefoneRepo.findByEmpresaIdAndId(empresa.getId(), idTelefone);
        telefoneRepo.delete(telefone);
    }
}
