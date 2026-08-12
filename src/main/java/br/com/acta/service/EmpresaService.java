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
import br.com.acta.dto.core.empresa.endereco.EnderecoRequestDTO;
import br.com.acta.dto.core.empresa.endereco.EnderecoResponseDTO;
import br.com.acta.dto.mapper.core.EmpresaMapper;
import br.com.acta.dto.mapper.core.EnderecoMapper;
import br.com.acta.dto.mapper.core.contato.EmailEmpresaMapper;
import br.com.acta.dto.mapper.core.contato.TelefoneEmpresaMapper;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Endereco;
import br.com.acta.entity.core.contato.EmailEmpresa;
import br.com.acta.entity.core.contato.TelefoneEmpresa;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TamanhoEmpresa;
import br.com.acta.repository.padrao.EmailEmpresaRepository;
import br.com.acta.repository.padrao.EmpresaRepository;
import br.com.acta.repository.padrao.EnderecoRepository;
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
    private final EnderecoMapper enderecoMapper;
    private final EnderecoRepository enderecoRepo;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("cnpj", "nome", "tamanho", "setor", "status"),
            Set.of("nome", "tamanho", "setor")
    );
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    public EmpresaService(EmpresaRepository repo, EmpresaMapper mapper, EmailEmpresaMapper emailMapper, EmailEmpresaRepository emailRepo, TelefoneEmpresaMapper telefoneMapper, TelefoneEmpresaRepository telefoneRepo, EnderecoMapper enderecoMapper, EnderecoRepository enderecoRepo) {
        super(repo, mapper, Empresa.class);
        this.repo = repo;
        this.mapper = mapper;
        this.emailMapper = emailMapper;
        this.emailRepo = emailRepo;
        this.telefoneMapper = telefoneMapper;
        this.telefoneRepo = telefoneRepo;
        this.enderecoMapper = enderecoMapper;
        this.enderecoRepo = enderecoRepo;
    }

    @Override
    @Transactional
    public EmpresaResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Empresa empresa = getEntity(id);

        if (campos.containsKey("nome")) empresa.setNome((String) campos.get("nome"));
        if (campos.containsKey("tamanho")) empresa.setTamanho((TamanhoEmpresa) campos.get("tamanho"));
        if (campos.containsKey("setor")) empresa.setSetor((String) campos.get("setor"));

        Empresa salvo = repo.save(empresa);
        return mapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> buscar(TamanhoEmpresa tamanho){
        List<Empresa> empresas;

        if (tamanho == null) empresas = repo.findAllByStatus(StatusGeral.ATIVO);
        else empresas = repo.findByTamanhoAndStatus(tamanho, StatusGeral.ATIVO);

        return mapper.toResponseList(empresas);
    }

    @Transactional(readOnly = true)
    @Override
    public EmpresaResponseDTO buscar(Long id) {
        Empresa empresa = getEntity(id);

        if (empresa.getStatus() == StatusGeral.INATIVO) throw new ModelNotFoundException("Empresa", id);
        return mapper.toResponse(empresa);
    }

    @Transactional
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

    @Transactional(readOnly = true)
    public List<EmailResponseDTO> buscarEmails(Long idEmpresa){
        Empresa empresa = getEntity(idEmpresa);
        List<EmailEmpresa> email = emailRepo.findByEmpresa_Id(empresa.getId());

        return emailMapper.toResponseList(email);
    }

    @Transactional
    public EmailResponseDTO inserirEmail(Long idEmpresa, EmailRequestDTO dto){
        Empresa empresa = getEntity(idEmpresa);

        if (emailRepo.existsByContatoIgnoreCase(dto.email())) throw new UniqueViolationException("E-mail");

        EmailEmpresa email = emailMapper.toEntity(dto);
        email.setEmpresa(empresa);

        EmailEmpresa salvo = emailRepo.save(email);
        return emailMapper.toResponse(salvo);
    }

    @Transactional
    public void excluirEmail(Long idEmpresa, Long idEmail){
        EmailEmpresa email = getEmail(idEmpresa, idEmail);
        emailRepo.delete(email);
    }

    @Transactional(readOnly = true)
    public List<TelefoneResponseDTO> buscarTelefones(Long idEmpresa){
        Empresa empresa = getEntity(idEmpresa);
        List<TelefoneEmpresa> telefone = telefoneRepo.findByEmpresa_Id(empresa.getId());

        return telefoneMapper.toResponseList(telefone);
    }

    @Transactional
    public TelefoneResponseDTO inserirTelefone(Long idEmpresa, TelefoneRequestDTO dto){
        Empresa empresa = getEntity(idEmpresa);
        TelefoneEmpresa telefone = telefoneMapper.toEntity(dto);

        if (telefoneRepo.existsByContatoIgnoreCase(dto.numero())) throw new UniqueViolationException("Telefone");

        telefone.setEmpresa(empresa);

        TelefoneEmpresa salvo = telefoneRepo.save(telefone);
        return telefoneMapper.toResponse(salvo);
    }

    @Transactional
    public void excluirTelefone(Long idEmpresa, Long idTelefone){
        TelefoneEmpresa telefone = getTelefone(idEmpresa, idTelefone);
        telefoneRepo.delete(telefone);
    }

    @Transactional(readOnly = true)
    public EnderecoResponseDTO buscarEndereco(Long idEmpresa, Long idEndereco) {
        Endereco endereco = getEndereco(idEmpresa, idEndereco);
        return enderecoMapper.toResponse(endereco);
    }

    @Transactional(readOnly = true)
    public List<EnderecoResponseDTO> buscarEndereco(Long idEmpresa) {
        Empresa empresa = getEntity(idEmpresa);
        List<Endereco> enderecos = empresa.getEnderecos();
        return enderecoMapper.toResponseList(enderecos);
    }

    @Transactional
    public EnderecoResponseDTO inserirEndereco(Long idEmpresa, EnderecoRequestDTO dto) {
        Empresa empresa = getEntity(idEmpresa);
        Endereco endereco = enderecoMapper.toEntity(dto);

        endereco.setEmpresa(empresa);
        Endereco salvo = enderecoRepo.save(endereco);
        return enderecoMapper.toResponse(salvo);
    }

    @Transactional
    public void excluirEndereco(Long idEmpresa, Long idEndereco) {
        Endereco endereco = getEndereco(idEmpresa, idEndereco);
        enderecoRepo.delete(endereco);
    }

    private Endereco getEndereco(Long idEmpresa, Long idEndereco){
        Empresa empresa = getEntity(idEmpresa);
        return enderecoRepo.findByEmpresaAndId(empresa, idEndereco).orElseThrow(() -> new ModelNotFoundException("Endereco", idEndereco));
    }

    private EmailEmpresa getEmail(Long idEmpresa, Long idEmail){
        Empresa empresa = getEntity(idEmpresa);
        return emailRepo.findByEmpresaIdAndId(empresa.getId(), idEmail).orElseThrow(() -> new ModelNotFoundException("Email", idEmail));
    }

    private TelefoneEmpresa getTelefone(Long idEmpresa, Long idTelefone){
        Empresa empresa = getEntity(idEmpresa);
        return telefoneRepo.findByEmpresaIdAndId(empresa.getId(), idTelefone).orElseThrow(() -> new ModelNotFoundException("Telefone", idTelefone));
    }
}
