package br.com.acta.service;

import br.com.acta.dto.pdca.plano_acao.PlanoAcaoRequestDTO;
import br.com.acta.dto.pdca.plano_acao.PlanoAcaoResponseDTO;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.handler.exception.BusinessRuleException;
import br.com.acta.handler.exception.StatusUpdateException;
import br.com.acta.mapper.pdca.PlanoAcaoMapper;
import br.com.acta.repository.padrao.PlanoAcaoRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.utils.PatchConfig;
import br.com.acta.utils.Validador;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class PlanoAcaoService
extends BaseService<PlanoAcaoRequestDTO, PlanoAcaoResponseDTO, PlanoAcao> {
    private final PlanoAcaoRepository repo;
    private final PlanoAcaoMapper mapper;
    private final CicloService cicloService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("nome", "objetivo", "prioridade", "origem", "idCiclo", "criadoPor"),
            Set.of("nome", "objetivo", "prioridade")
    );

    public PlanoAcaoService(PlanoAcaoRepository repo, PlanoAcaoMapper mapper, CicloService cicloService) {
        super(repo, mapper, PlanoAcao.class);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
    }

    @Override
    public PlanoAcaoResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        PlanoAcao planoAcao = getEntity(id);

        if (campos.containsKey("nome")) planoAcao.setNome((String) campos.get("nome"));
        if (campos.containsKey("objetivo")) planoAcao.setObjetivo((String) campos.get("objetivo"));
        if (campos.containsKey("prioridade")) planoAcao.setPrioridade((Prioridade) campos.get("prioridade"));

        PlanoAcao salvo = repo.save(planoAcao);
        return mapper.toResponse(salvo);
    }

    public PlanoAcaoResponseDTO atualizarStatus(Long id, StatusPlanoAcao status) {
        PlanoAcao planoAcao = getEntity(id);

        if (!planoAcao.getStatus().podeAtualizarStatus(status)) {
            throw new StatusUpdateException(planoAcao.getStatus().toString(), status.toString());
        }

        if (planoAcao.getPlano5W2H() == null && status == StatusPlanoAcao.APROVADO){
            throw new BusinessRuleException("Um plano de ação não pode ser aprovado sem o 5W2H");
        }

        planoAcao.setStatus(status);
        PlanoAcao salvo = repo.save(planoAcao);
        return mapper.toResponse(salvo);
    }

    @Override
    public void excluir(Long id) {
        PlanoAcao planoAcao = getEntity(id);

        // todo verificar dependencia de tarefa
        if (planoAcao.getStatus() == StatusPlanoAcao.CONCLUIDO) {
            throw new BusinessRuleException("Um plano de ação concluído não pode ser excluído");
        }

        planoAcao.setStatus(StatusPlanoAcao.CANCELADO);
        repo.save(planoAcao);
    }

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

    // todo adicionar criadoPor
    public PlanoAcaoResponseDTO inserir(PlanoAcaoRequestDTO dto, Long idCiclo) {
        PlanoAcao planoAcao = mapper.toEntity(dto);
        Ciclo ciclo = cicloService.getEntity(idCiclo);

        planoAcao.setCiclo(ciclo);

        PlanoAcao salvo = repo.save(planoAcao);
        return mapper.toResponse(salvo);
    }
}
