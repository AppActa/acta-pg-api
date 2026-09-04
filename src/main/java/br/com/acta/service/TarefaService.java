package br.com.acta.service;

import br.com.acta.common.handler.exception.*;
import br.com.acta.common.utils.ConversorObject;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.mapper.pdca.TarefaMapper;
import br.com.acta.dto.pdca.tarefa.TarefaRequestDTO;
import br.com.acta.dto.pdca.tarefa.TarefaResponseDTO;
import br.com.acta.dto.pdca.tarefa.TarefaStatusUpdateDTO;
import br.com.acta.dto.pdca.tarefa.TarefaSummaryResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
import br.com.acta.entity.enums.StatusTarefa;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.repository.padrao.TarefaRepository;
import br.com.acta.service.base.BaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TarefaService
extends BaseService<TarefaRequestDTO, TarefaResponseDTO, Tarefa> {
    private final TarefaRepository repo;
    private final TarefaMapper mapper;
    private final PlanoAcaoService planoAcaoService;
    private final UsuarioService usuarioService;
    private final PatchConfig patchConfigConfig = new PatchConfig(
            Set.of("titulo", "descricao", "prioridade", "dataInicioReal", "dataFimReal", "dataFimPrevista", "idResponsavel", "status"),
            Set.of("titulo", "descricao", "prioridade", "dataFimPrevista")
    );

    public TarefaService(TarefaRepository repo, TarefaMapper mapper, PlanoAcaoService planoAcaoService, UsuarioService usuarioService, AuthService authService) {
        super(repo, mapper, Tarefa.class, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.planoAcaoService = planoAcaoService;
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public TarefaResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfigConfig);
        Tarefa tarefa = getEntity(id);
        Validador.validarTarefaAberta(tarefa);

        if (campos.containsKey("titulo")) tarefa.setTitulo((String) campos.get("titulo"));
        if (campos.containsKey("descricao")) tarefa.setDescricao((String) campos.get("descricao"));

        if (campos.containsKey("prioridade")) {
            Object prioridadeObject = campos.get("prioridade");
            tarefa.setPrioridade(ConversorObject.toEnum(prioridadeObject, Prioridade.class));
        }

        if (campos.containsKey("dataFimPrevista")) {
            Object dataFimPrevistaObject = campos.get("dataFimPrevista");
            tarefa.setDataFimPrevista(ConversorObject.toLocalDate(dataFimPrevistaObject, false));
        }

        Tarefa salvo = repo.save(tarefa);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<TarefaResponseDTO> buscar(Long idPlanoAcao, StatusTarefa status, Long idResponsavel, Prioridade prioridade){
        planoAcaoService.getEntity(idPlanoAcao);
        List<Tarefa> tarefas = repo.buscar(idPlanoAcao, status, idResponsavel, prioridade);

       return mapper.toResponseList(tarefas);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public TarefaResponseDTO inserir(Long idPlanoAcao, TarefaRequestDTO dto) {
        Tarefa tarefa = mapper.toEntity(dto);
        PlanoAcao planoAcao = planoAcaoService.getEntity(idPlanoAcao);
        Usuario usuario = usuarioService.getEntity(dto.idResponsavel());
        Validador.validarCicloAberto(planoAcao.getCiclo());

        if (!Set.of(StatusPlanoAcao.APROVADO, StatusPlanoAcao.EM_EXECUCAO).contains(planoAcao.getStatus())){
            throw new InvalidResourceStatusException("Plano de Ação", List.of(StatusPlanoAcao.APROVADO.toString(), StatusPlanoAcao.EM_EXECUCAO.toString()));
        }

        tarefa.setResponsavel(usuario);
        tarefa.setPlanoAcao(planoAcao);
        tarefa.setStatus(StatusTarefa.PENDENTE);

        Tarefa salvo = repo.save(tarefa);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public TarefaResponseDTO patchStatus(Long id, TarefaStatusUpdateDTO dto){
        Tarefa tarefa = getEntity(id);
        Validador.validarCicloAberto(tarefa.getPlanoAcao().getCiclo());
        if (!tarefa.getStatus().podeAtualizarStatus(dto.status())) {
            throw new StatusUpdateException(tarefa.getStatus().toString(), dto.status().toString());
        }

        switch (dto.status()) {
            case PENDENTE -> {
                tarefa.setDataInicioReal(null);
                tarefa.setDataFimReal(null);
            }

            case EM_ANDAMENTO -> {
                if (!repo.podeIniciarTarefa(tarefa.getId(), atual().idUsuario()))
                    throw new PrerequisiteNotMetException("iniciar tarefa", "usuário não pode iniciar essa tarefa");

                LocalDate dataInicio = capturarData(dto.dataInicioReal());
                tarefa.setDataInicioReal(dataInicio);
            }

            case CONCLUIDA -> {
                LocalDate dataFim = capturarData(dto.dataFimReal());
                tarefa.setDataFimReal(dataFim);
            }

            case ATRASADA -> throw new StatusUpdateException(tarefa.getStatus().toString(), dto.status().toString());
        }

        tarefa.setStatus(dto.status());
        Tarefa salva = repo.save(tarefa);
        return mapper.toResponse(salva);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public TarefaResponseDTO reatribuir(Long idTarefa, Long idResponsavel){
        Tarefa tarefa = getEntity(idTarefa);
        Usuario responsavel = usuarioService.getEntity(idResponsavel);

        Validador.validarMesmoCiclo(tarefa.getPlanoAcao().getCiclo(), responsavel.getCiclos());
        Validador.validarTarefaAberta(tarefa);

        tarefa.setResponsavel(responsavel);
        Tarefa salvo = repo.save(tarefa);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public TarefaResponseDTO reabrir(Long idTarefa, LocalDate novoPrazo){
        repo.reabrirTarefa(idTarefa, novoPrazo);

        Tarefa tarefa = getEntity(idTarefa);
        return mapper.toResponse(tarefa);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public void excluir(Long id) {
        Tarefa tarefa = getEntity(id);
        Validador.validarCicloAberto(tarefa.getPlanoAcao().getCiclo());
        if (!tarefa.getDependentes().isEmpty())
            throw new ActiveEntityDeletionException("Tarefa");

        tarefa.setStatus(StatusTarefa.CANCELADA);
        repo.save(tarefa);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<TarefaSummaryResponseDTO> buscarDependentes(Long id){
        Tarefa tarefa = getEntity(id);
        Set<Tarefa> dependentes = tarefa.getDependentes();

        return mapper.toSummaryList(dependentes);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public TarefaResponseDTO adicionarDependencia(Long id, Long idDependente){
        Tarefa tarefa = getEntity(id);
        Tarefa dependenteNovo = getEntity(idDependente);

        Validador.validarMesmoId(id, idDependente, false);
        Validador.validarMesmoCiclo(tarefa.getPlanoAcao().getCiclo(), dependenteNovo.getPlanoAcao().getCiclo());

        if (tarefa.getDependentes().contains(dependenteNovo))
            throw new UniqueViolationException("Dependência de tarefa");

        if (criaDependenciaCircular(tarefa, dependenteNovo))
            throw new CircularDependencyException("Tarefas");

        tarefa.getDependentes().add(dependenteNovo);
        dependenteNovo.getDependencias().add(tarefa);

        repo.save(dependenteNovo);
        Tarefa salvo = repo.save(tarefa);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public void removerDependencia(Long id, Long idDependente){
        Tarefa tarefa = getEntity(id);
        Tarefa dependente = getEntity(idDependente);

        Validador.validarMesmoCiclo(tarefa.getPlanoAcao().getCiclo(), dependente.getPlanoAcao().getCiclo());
        if ( !tarefa.getDependentes().contains(dependente) ) {
            throw new InvalidRelationshipException(tarefa.getTitulo(), dependente.getTitulo(), "ter uma dependência entre elas");
        }

        tarefa.getDependentes().remove(dependente);
        dependente.getDependencias().remove(tarefa);

        repo.save(dependente);
        repo.save(tarefa);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public TarefaResponseDTO buscar(Long id) {
        return super.buscar(id);
    }

    private LocalDate capturarData(LocalDate data){
        return data != null ? data : LocalDate.now();
    }

    private boolean criaDependenciaCircular(Tarefa tarefa, Tarefa dependente) {
        if (tarefa.getId().equals(dependente.getId())) return true;

        for (Tarefa dep : tarefa.getDependentes()) {
            if (criaDependenciaCircular(dep, dependente)) return true;
        }

        return false;
    }
}
