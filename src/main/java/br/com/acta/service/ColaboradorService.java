package br.com.acta.service;

import br.com.acta.common.handler.exception.ActiveEntityDeletionException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.handler.exception.RegexException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.core.colaborador.ColaboradorRequestDTO;
import br.com.acta.dto.core.colaborador.ColaboradorResponseDTO;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.dto.core.usuario.UsuarioResponseDTO;
import br.com.acta.dto.mapper.core.ColaboradorMapper;
import br.com.acta.dto.mapper.core.contato.EmailColaboradorMapper;
import br.com.acta.dto.mapper.core.contato.TelefoneColaboradorMapper;
import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.core.contato.EmailColaborador;
import br.com.acta.entity.core.contato.TelefoneColaborador;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.StatusTarefa;
import br.com.acta.repository.padrao.ColaboradorRepository;
import br.com.acta.repository.padrao.EmailColaboradorRepository;
import br.com.acta.repository.padrao.TarefaRepository;
import br.com.acta.repository.padrao.TelefoneColaboradorRepository;
import br.com.acta.service.base.BaseService;
import br.com.caelum.stella.validation.CPFValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ColaboradorService extends BaseService<ColaboradorRequestDTO, ColaboradorResponseDTO, Colaborador> {
    private final ColaboradorRepository repo;
    private final ColaboradorMapper mapper;
    private final EmpresaService empresaService;
    private final UsuarioService usuarioService;
    private final TarefaRepository tarefaRepo;
    private final EmailColaboradorRepository emailRepo;
    private final TelefoneColaboradorRepository telefoneRepo;
    private final EmailColaboradorMapper emailMapper;
    private final TelefoneColaboradorMapper telefoneMapper;
    private final CPFValidator CPFValidator = new CPFValidator();
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("cpf", "nome", "cargo", "area", "dataNascimento", "dataContratacao", "permissaoGestor", "status", "emails", "telefones", "usuario", "idEmpresa"),
            Set.of("nome", "cargo", "area", "permissaoGestor", "status")
    );

    public ColaboradorService(ColaboradorRepository repo, ColaboradorMapper mapper, EmpresaService empresaService, UsuarioService usuarioService, TarefaRepository tarefaRepo, EmailColaboradorRepository emailRepo, TelefoneColaboradorRepository telefoneRepo, EmailColaboradorMapper emailMapper, TelefoneColaboradorMapper telefoneMapper, AuthService authService) {
        super(repo, mapper, Colaborador.class, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
        this.tarefaRepo = tarefaRepo;
        this.emailRepo = emailRepo;
        this.telefoneRepo = telefoneRepo;
        this.emailMapper = emailMapper;
        this.telefoneMapper = telefoneMapper;
    }

    @Override
    public List<ColaboradorResponseDTO> buscar() {
        List<Colaborador> colaboradores = repo.findAllByStatus(StatusGeral.ATIVO);
        return mapper.toResponseList(colaboradores);
    }

    @Override
    public ColaboradorResponseDTO buscar(Long id){
        Colaborador colaborador = getEntity(id);

        if (colaborador.getStatus() == StatusGeral.INATIVO) throw new ModelNotFoundException("Colaborador", id);

        return mapper.toResponse(colaborador);
    }

    public List<ColaboradorResponseDTO> buscarPorEmpresa(Long idEmpresa){
        Empresa empresa = empresaService.getEntity(idEmpresa);
        Set<Colaborador> colaboradores = empresa.getColaboradores();

        return mapper.toResponseList(colaboradores);
    }

    @Override
    protected void antesInserir(Colaborador colaborador, ColaboradorRequestDTO dto) {
        if (!CPFValidator.isEligible(dto.cpf())) throw new RegexException("CPF");
        if (repo.existsByCpf(dto.cpf())) throw new UniqueViolationException("CPF");
        Validador.validarMesmoId(dto.idEmpresa(), dto.usuario().idEmpresa(), true);
    }

    @Override
    @Transactional
    public ColaboradorResponseDTO inserir(ColaboradorRequestDTO dto) {
        Colaborador colaborador = mapper.toEntity(dto);
        antesInserir(colaborador, dto);

        Empresa empresa = empresaService.getEntity(dto.idEmpresa());

        UsuarioResponseDTO usuarioResp = usuarioService.inserir(dto.usuario());
        Usuario usuario = usuarioService.getEntity(usuarioResp.id());

        colaborador.setEmpresa(empresa);
        colaborador.setUsuario(usuario);
        colaborador.setStatus(StatusGeral.ATIVO);

        dto.emails().forEach(email -> {
            if (emailRepo.existsByContatoIgnoreCase(email.email())) throw new UniqueViolationException("E-mail");

            EmailColaborador emailColaborador = emailMapper.toEntity(email);
            emailColaborador.setColaborador(colaborador);
            colaborador.getEmails().add(emailColaborador);
        });

        dto.telefones().forEach(telefone -> {
            if (telefoneRepo.existsByContatoIgnoreCase(telefone.numero())) throw new UniqueViolationException("Telefone");

            TelefoneColaborador telefoneColaborador = telefoneMapper.toEntity(telefone);
            telefoneColaborador.setColaborador(colaborador);
            colaborador.getTelefones().add(telefoneColaborador);
        });

        Colaborador salvo = repo.save(colaborador);
        return mapper.toResponse(salvo);
    }

    @Override
    public ColaboradorResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Colaborador colaborador = getEntity(id);

        if (colaborador.getStatus() == StatusGeral.INATIVO) throw new ModelNotFoundException("Colaborador", id);

        if (campos.containsKey("nome")) colaborador.setNome((String) campos.get("nome"));
        if (campos.containsKey("cargo")) colaborador.setCargo((String) campos.get("cargo"));
        if ( campos.containsKey("area")) colaborador.setArea((String) campos.get("area"));
        if (campos.containsKey("permissaoGestor")) colaborador.setPermissaoGestor((Boolean) campos.get("permissaoGestor"));

        Colaborador salvo = repo.save(colaborador);
        return mapper.toResponse(salvo);
    }

    @Override
    public void excluir(Long id) {
        Colaborador colaborador = getEntity(id);
        boolean possuiTarefa = tarefaRepo.findByResponsavelId(colaborador.getUsuario().getId()).stream()
                .anyMatch(tarefa -> tarefa.getStatus() != StatusTarefa.CONCLUIDA && tarefa.getStatus() != StatusTarefa.CANCELADA);

        if (possuiTarefa) throw new ActiveEntityDeletionException("Colaborador");
        colaborador.setStatus(StatusGeral.INATIVO);
        repo.save(colaborador);
    }

    public List<EmailResponseDTO> buscarEmails(Long idColaborador){
        Colaborador colaborador = getEntity(idColaborador);
        List<EmailColaborador> email = emailRepo.findByColaborador_Id(colaborador.getId());

        return emailMapper.toResponseList(email);
    }

    public EmailResponseDTO inserirEmail(Long idColaborador, EmailRequestDTO dto){
        Colaborador colaborador = getEntity(idColaborador);

        if (emailRepo.existsByColaboradorIdAndContatoIgnoreCase(idColaborador, dto.email())) throw new UniqueViolationException("E-mail");

        EmailColaborador email = emailMapper.toEntity(dto);
        email.setColaborador(colaborador);

        EmailColaborador salvo = emailRepo.save(email);
        return emailMapper.toResponse(salvo);
    }

    public void excluirEmail(Long idColaborador, Long idEmail){
        EmailColaborador email = getEmail(idColaborador, idEmail);
        emailRepo.delete(email);
    }

    public List<TelefoneResponseDTO> buscarTelefones(Long idColaborador){
        Colaborador colaborador = getEntity(idColaborador);
        List<TelefoneColaborador> telefone = telefoneRepo.findByColaborador_Id(colaborador.getId());

        return telefoneMapper.toResponseList(telefone);
    }

    public TelefoneResponseDTO inserirTelefone(Long idColaborador, TelefoneRequestDTO dto){
        Colaborador colaborador = getEntity(idColaborador);
        if (telefoneRepo.existsByColaboradorIdAndContato(idColaborador, dto.numero())) throw new UniqueViolationException("Telefone");

        TelefoneColaborador telefone = telefoneMapper.toEntity(dto);
        telefone.setColaborador(colaborador);

        TelefoneColaborador salvo = telefoneRepo.save(telefone);
        return telefoneMapper.toResponse(salvo);
    }

    public void excluirTelefone(Long idColaborador, Long idTelefone){
        TelefoneColaborador telefone = getTelefone(idColaborador, idTelefone);
        telefoneRepo.delete(telefone);
    }

    private EmailColaborador getEmail(Long idColaborador, Long idEmail){
        Colaborador colaborador = getEntity(idColaborador);
        return emailRepo.findByColaboradorIdAndId(colaborador.getId(), idEmail).orElseThrow(() -> new ModelNotFoundException("Email", idEmail));
    }

    private TelefoneColaborador getTelefone(Long idColaborador, Long idTelefone){
        Colaborador colaborador = getEntity(idColaborador);
        return telefoneRepo.findByColaboradorIdAndId(colaborador.getId(), idTelefone).orElseThrow(() -> new ModelNotFoundException("Telefone", idTelefone));
    }
}