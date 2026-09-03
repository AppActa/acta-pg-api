package br.com.acta.service;

import br.com.acta.common.handler.exception.ActiveEntityDeletionException;
import br.com.acta.common.handler.exception.PrerequisiteNotMetException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.common.utils.ConversorObject;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.core.usuario.UsuarioSummaryResponseDTO;
import br.com.acta.dto.mapper.core.UsuarioMapper;
import br.com.acta.dto.mapper.pdca.MetaMapper;
import br.com.acta.dto.pdca.meta.MetaRequestDTO;
import br.com.acta.dto.pdca.meta.MetaResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusMeta;
import br.com.acta.entity.enums.StatusTarefa;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.Meta;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.repository.padrao.MetaRepository;
import br.com.acta.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MetaService
extends BaseService<MetaRequestDTO, MetaResponseDTO, Meta> {
    private final MetaRepository repo;
    private final MetaMapper mapper;
    private final UsuarioService usuarioService;
    private final PlanoAcaoService planoAcaoService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("objetivo", "responsaveis", "status", "prioridade", "valorBase", "valorAlvo", "unidadeMedida", "prazo", "area", "categoria"),
            Set.of("objetivo", "valorAlvo", "prazo", "prioridade", "area", "categoria")
    );
    private final UsuarioMapper usuarioMapper;

    public MetaService(MetaRepository repo, MetaMapper mapper, UsuarioService usuarioService, PlanoAcaoService planoAcaoService, UsuarioMapper usuarioMapper, AuthService authService) {
        super(repo, mapper, Meta.class, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.usuarioService = usuarioService;
        this.planoAcaoService = planoAcaoService;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    @Override
    public MetaResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Meta meta = getEntity(id);

        if(campos.containsKey("objetivo")) meta.setObjetivo((String) campos.get("objetivo"));
        if(campos.containsKey("area")) meta.setArea((String) campos.get("area"));
        if(campos.containsKey("categoria")) meta.setCategoria((String) campos.get("categoria"));

        if ( campos.containsKey("valorAlvo")) {
            Object valorAlvoObject = campos.get("valorAlvo");
            meta.setValorAlvo(ConversorObject.toBigDecimal(valorAlvoObject));
        }

        if (campos.containsKey("prazo")) {
            Object dataObject = campos.get("prazo");
            meta.setPrazo(ConversorObject.toLocalDate(dataObject, false));
        }

        if (campos.containsKey("prioridade")) {
            Object prioridadeObject = campos.get("prioridade");
            meta.setPrioridade(ConversorObject.toEnum(prioridadeObject, Prioridade.class));
        }

        Meta salvo = repo.save(meta);
        return mapper.toResponse(salvo);
    }

    @Transactional
    public MetaResponseDTO inserir(Long idPlanoAcao, MetaRequestDTO dto) {
        PlanoAcao planoAcao = planoAcaoService.getEntity(idPlanoAcao);
        Ciclo ciclo = planoAcao.getCiclo();
        Validador.validarCicloAberto(ciclo);

        Meta meta = mapper.toEntity(dto);
        Set<Usuario> responsaveis = new HashSet<>();

        meta.setCiclo(ciclo);
        meta.setPlanoAcao(planoAcao);
        meta.setStatus(StatusMeta.NAO_INICIADA);

        dto.responsaveis().forEach(idResponsavel -> {
            Usuario usuario = usuarioService.getEntity(idResponsavel);
            Validador.validarMesmoCiclo(ciclo, usuario.getCiclos());

            responsaveis.add(usuario);
        });

        meta.setResponsaveis(responsaveis);

        Meta salvo = repo.save(meta);
        return mapper.toResponse(salvo);
    }

    @Transactional
    public MetaResponseDTO patchStatus(Long id, StatusMeta status){
        Meta meta = getEntity(id);

        meta.setStatus(status);

        Meta salvo = repo.save(meta);
        return mapper.toResponse(salvo);
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        Meta meta = getEntity(id);
        List<Tarefa> tarefas = meta.getPlanoAcao().getTarefas();

        boolean temTarefasAtivas = tarefas.stream()
                .anyMatch(tarefa -> tarefa.getStatus() != StatusTarefa.CONCLUIDA && tarefa.getStatus() != StatusTarefa.CANCELADA);

        if (temTarefasAtivas) {
            throw new ActiveEntityDeletionException("Meta");
        }

        meta.setStatus(StatusMeta.CANCELADA);
        repo.save(meta);
    }

    @Transactional
    public List<MetaResponseDTO> buscar(Long idCiclo, StatusMeta status, Prioridade prioridade) {
        List<Meta> metas;

        if (status == null && prioridade == null){
            metas = repo.findByCicloId(idCiclo);
        } else if (status == null){
            metas = repo.findByCicloIdAndPrioridade(idCiclo, prioridade);
        } else if (prioridade == null){
            metas = repo.findByCicloIdAndStatus(idCiclo, status);
        } else {
            metas = repo.findByCicloIdAndStatusAndPrioridade(idCiclo, status, prioridade);
        }

        return mapper.toResponseList(metas);
    }

    @Transactional(readOnly = true)
    public List<UsuarioSummaryResponseDTO> buscarResponsaveis(Long id){
        Meta meta = getEntity(id);
        Set<Usuario> responsaveis = meta.getResponsaveis();

        return usuarioMapper.toSummaryList(responsaveis);
    }

    @Transactional
    public List<UsuarioSummaryResponseDTO> inserirResponsaveis(Long idMeta, List<Long> idResponsaveis){
        Meta meta = getEntity(idMeta);
        Set<Usuario> responsaveisAtuais = meta.getResponsaveis();
        Set<Long> usuariosJaResponsaveis = responsaveisAtuais.stream()
                .map(Usuario::getId)
                .collect(Collectors.toSet());

        for (Long idResponsavel : idResponsaveis){
            // valida usuários já cadastrados e repetidos na lista de entrada
            if (!usuariosJaResponsaveis.add(idResponsavel)) throw new UniqueViolationException("Responsável", "Meta");

            Usuario usuario = usuarioService.getEntity(idResponsavel);
            Validador.validarMesmoCiclo(meta.getCiclo(), usuario.getCiclos());
            responsaveisAtuais.add(usuario);
        }

        repo.save(meta);
        return usuarioMapper.toSummaryList(responsaveisAtuais);
    }

    @Transactional
    public void excluirResponsaveis(Long idMeta, List<Long> idResponsaveis){
        Meta meta = getEntity(idMeta);
        Set<Usuario> responsaveisAtuais = meta.getResponsaveis();

        if (responsaveisAtuais.size() == 1) {
            throw new PrerequisiteNotMetException("excluir responsável", "pelo menos dois responsáveis");
        }

        for (Long idResponsavel : idResponsaveis) {
            responsaveisAtuais.removeIf(usuario -> usuario.getId().equals(idResponsavel));
        }

        repo.save(meta);
    }
}
