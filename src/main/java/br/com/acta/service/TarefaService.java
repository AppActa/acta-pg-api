package br.com.acta.service;

import br.com.acta.common.handler.exception.*;
import br.com.acta.dto.pdca.tarefa.TarefaRequestDTO;
import br.com.acta.dto.pdca.tarefa.TarefaResponseDTO;
import br.com.acta.dto.pdca.tarefa.TarefaStatusUpdateDTO;
import br.com.acta.dto.pdca.tarefa.TarefaSummaryResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
import br.com.acta.entity.enums.StatusTarefa;
import br.com.acta.entity.enums.StatusTreinamento;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.dto.mapper.pdca.TarefaMapper;
import br.com.acta.repository.composto.UsuarioTreinamentoRepository;
import br.com.acta.repository.padrao.TarefaRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TarefaService
extends BaseService<TarefaRequestDTO, TarefaResponseDTO, Tarefa> {
    private final TarefaRepository repo;
    private final TarefaMapper mapper;
    private final UsuarioTreinamentoRepository usuarioTreinamentoRepo;
    private final PlanoAcaoService planoAcaoService;
    private final UsuarioService usuarioService;
    private final PatchConfig patchConfigConfig = new PatchConfig(
            Set.of("titulo", "descricao", "prioridade", "dataInicioReal", "dataFimReal", "dataFimPrevista", "idResponsavel", "status"),
            Set.of("titulo", "descricao", "prioridade", "dataFimPrevista")
    );

    public TarefaService(TarefaRepository repo, TarefaMapper mapper, PlanoAcaoService planoAcaoService, UsuarioService usuarioService, UsuarioTreinamentoRepository usuarioTreinamentoRepo) {
        super(repo, mapper, Tarefa.class);
        this.repo = repo;
        this.mapper = mapper;
        this.planoAcaoService = planoAcaoService;
        this.usuarioService = usuarioService;
        this.usuarioTreinamentoRepo = usuarioTreinamentoRepo;
    }

    @Override
    public TarefaResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfigConfig);
        Tarefa tarefa = getEntity(id);
        Validador.validarTarefaAberta(tarefa);

        if (campos.containsKey("titulo")) tarefa.setTitulo((String) campos.get("titulo"));
        if (campos.containsKey("descricao")) tarefa.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("prioridade")) tarefa.setPrioridade((Prioridade) campos.get("prioridade"));
        if (campos.containsKey("dataFimPrevista")) tarefa.setDataFimPrevista((LocalDate) campos.get("dataFimPrevista"));

        Tarefa salvo = repo.save(tarefa);
        return mapper.toResponse(salvo);
    }

    public List<TarefaResponseDTO> buscar(Long idPlanoAcao, StatusTarefa status, Long idResponsavel, Prioridade prioridade){
        planoAcaoService.getEntity(idPlanoAcao);
        List<Tarefa> tarefas = repo.buscar(idPlanoAcao, status, idResponsavel, prioridade);

       return mapper.toResponseList(tarefas);
    }

    public TarefaResponseDTO inserir(Long idPlanoAcao, TarefaRequestDTO dto) {
        Tarefa tarefa = mapper.toEntity(dto);
        PlanoAcao planoAcao = planoAcaoService.getEntity(idPlanoAcao);
        Validador.validarCicloAberto(planoAcao.getCiclo());

        if (!Set.of(StatusPlanoAcao.APROVADO, StatusPlanoAcao.EM_EXECUCAO).contains(planoAcao.getStatus())){
            throw new InvalidResourceStatusException("Plano de Ação", List.of(StatusPlanoAcao.APROVADO.toString(), StatusPlanoAcao.EM_EXECUCAO.toString()));
        }

        tarefa.setPlanoAcao(planoAcao);

        Tarefa salvo = repo.save(tarefa);
        return mapper.toResponse(salvo);
    }

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
                Long idResponsavel = tarefa.getResponsavel().getId();
                Long idCiclo = tarefa.getPlanoAcao().getCiclo().getId();
                boolean treinamentoPendente = usuarioTreinamentoRepo.existsByUsuarioIdAndTreinamentoCicloIdAndObrigatorioTrueAndStatus(idResponsavel, idCiclo, StatusTreinamento.PENDENTE);

                if (treinamentoPendente) {
                    throw new PrerequisiteNotMetException("atualizar status", "responsável possuir treinamento obrigatório ainda não iniciado");
                }

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

    public TarefaResponseDTO reatribuir(Long idTarefa, Long idResponsavel){
        Tarefa tarefa = getEntity(idTarefa);
        Usuario responsavel = usuarioService.getEntity(idResponsavel);

        Validador.validarMesmoCiclo(tarefa.getPlanoAcao().getCiclo(), responsavel.getCiclos());
        Validador.validarTarefaAberta(tarefa);

        tarefa.setResponsavel(responsavel);
        Tarefa salvo = repo.save(tarefa);
        return mapper.toResponse(salvo);
    }

    public TarefaResponseDTO reabrir(Long idTarefa, LocalDate novoPrazo){
        Tarefa tarefa = getEntity(idTarefa);
        Validador.validarCicloAberto(tarefa.getPlanoAcao().getCiclo());
        if (tarefa.getStatus() != StatusTarefa.CONCLUIDA && tarefa.getStatus() != StatusTarefa.CANCELADA) {
            throw new InvalidResourceStatusException("Tarefa", List.of(StatusTarefa.CONCLUIDA.toString(), StatusTarefa.CANCELADA.toString()));
        }

        tarefa.setStatus(StatusTarefa.EM_ANDAMENTO);
        tarefa.setDataFimReal(null);
        tarefa.setDataFimPrevista(novoPrazo);

        Tarefa salvo = repo.save(tarefa);
        return mapper.toResponse(salvo);
    }

    @Override
    public void excluir(Long id) {
        Tarefa tarefa = getEntity(id);
        Validador.validarCicloAberto(tarefa.getPlanoAcao().getCiclo());
        if (!tarefa.getDependentes().isEmpty())
            throw new ActiveEntityDeletionException("Tarefa");

        tarefa.setStatus(StatusTarefa.CANCELADA);
        repo.save(tarefa);
    }

    public List<TarefaSummaryResponseDTO> buscarDependentes(Long id){
        Tarefa tarefa = getEntity(id);
        Set<Tarefa> dependentes = tarefa.getDependentes();

        return mapper.toSummaryList(dependentes);
    }

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
