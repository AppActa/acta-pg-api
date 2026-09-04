package br.com.acta.service;

import br.com.acta.common.handler.exception.*;
import br.com.acta.common.utils.ConversorObject;
import br.com.acta.dto.pdca.plano_acao.PlanoAcaoRequestDTO;
import br.com.acta.dto.pdca.plano_acao.PlanoAcaoResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
import br.com.acta.entity.enums.StatusTarefa;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.dto.mapper.pdca.PlanoAcaoMapper;
import br.com.acta.repository.padrao.PlanoAcaoRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PlanoAcaoService
extends BaseService<PlanoAcaoRequestDTO, PlanoAcaoResponseDTO, PlanoAcao> {
    private final PlanoAcaoRepository repo;
    private final PlanoAcaoMapper mapper;
    private final CicloService cicloService;
    private final UsuarioService usuarioService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("nome", "objetivo", "prioridade", "origem", "idCiclo", "criadoPor"),
            Set.of("nome", "objetivo", "prioridade")
    );

    public PlanoAcaoService(PlanoAcaoRepository repo, PlanoAcaoMapper mapper, CicloService cicloService, UsuarioService usuarioService, AuthService authService) {
        super(repo, mapper, PlanoAcao.class, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public PlanoAcaoResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        PlanoAcao planoAcao = getEntity(id);

        if (campos.containsKey("nome")) planoAcao.setNome((String) campos.get("nome"));
        if (campos.containsKey("objetivo")) planoAcao.setObjetivo((String) campos.get("objetivo"));
        if (campos.containsKey("prioridade")) {
            Object prioridadeObject = campos.get("prioridade");
            planoAcao.setPrioridade(ConversorObject.toEnum(prioridadeObject, Prioridade.class));
        }

        PlanoAcao salvo = repo.save(planoAcao);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public PlanoAcaoResponseDTO patchStatus(Long id, StatusPlanoAcao status) {
        PlanoAcao planoAcao = getEntity(id);

        Validador.validarCicloAberto(planoAcao.getCiclo());

        if (!planoAcao.getStatus().podeAtualizarStatus(status)) {
            throw new StatusUpdateException(planoAcao.getStatus().toString(), status.toString());
        }

        if (planoAcao.getPlano5W2H() == null && status == StatusPlanoAcao.APROVADO){
            throw new PrerequisiteNotMetException("aprovar o plano de ação", "ter 5W2H");
        }

        planoAcao.setStatus(status);
        PlanoAcao salvo = repo.save(planoAcao);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public void excluir(Long id) {
        PlanoAcao planoAcao = getEntity(id);
        boolean temTarefasAtivas = planoAcao.getTarefas().stream()
                .anyMatch(tarefa -> tarefa.getStatus() != StatusTarefa.CONCLUIDA && tarefa.getStatus() != StatusTarefa.CANCELADA);

        if (temTarefasAtivas) {
            throw new ActiveEntityDeletionException("Plano de ação");
        }

        if (planoAcao.getStatus() == StatusPlanoAcao.CONCLUIDO){
            throw new InvalidResourceStatusException("excluir", "Plano de Ação", StatusPlanoAcao.CONCLUIDO.toString());
        }

        planoAcao.setStatus(StatusPlanoAcao.CANCELADO);
        repo.save(planoAcao);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<PlanoAcaoResponseDTO> buscar(Long id, StatusPlanoAcao status, Prioridade prioridade){
        List<PlanoAcao> planosAcao;

        if (status == null && prioridade == null){
            planosAcao = repo.findByCicloId(id);
        } else if (status == null) {
            planosAcao = repo.findByCicloIdAndPrioridade(id, prioridade);
        } else if (prioridade == null){
            planosAcao = repo.findByCicloIdAndStatus(id, status);
        } else {
            planosAcao = repo.findByCicloIdAndStatusAndPrioridade(id, status, prioridade);
        }

        return mapper.toResponseList(planosAcao);
    }

    @Transactional
    public PlanoAcaoResponseDTO inserir(PlanoAcaoRequestDTO dto, Long idCiclo, Long idCriadoPor) {
        PlanoAcao planoAcao = mapper.toEntity(dto);
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        Validador.validarCicloAberto(ciclo);

        Usuario criadoPor = usuarioService.getEntity(idCriadoPor);
        Validador.validarMesmoCiclo(ciclo, criadoPor.getCiclos());

        planoAcao.setCiclo(ciclo);
        planoAcao.setCriadoPor(criadoPor);
        planoAcao.setStatus(StatusPlanoAcao.RASCUNHO);

        PlanoAcao salvo = repo.save(planoAcao);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public PlanoAcaoResponseDTO buscar(Long id) {
        return super.buscar(id);
    }
}
