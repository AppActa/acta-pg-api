package br.com.acta.service;

import br.com.acta.common.handler.exception.BusinessRuleException;
import br.com.acta.common.handler.exception.StatusUpdateException;
import br.com.acta.dto.pdca.problema.ProblemaRequestDTO;
import br.com.acta.dto.pdca.problema.ProblemaResponseDTO;
import br.com.acta.entity.enums.StatusPlanoAcao;
import br.com.acta.entity.enums.StatusProblema;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.Problema;
import br.com.acta.dto.mapper.pdca.ProblemaMapper;
import br.com.acta.repository.padrao.CausaRaizRepository;
import br.com.acta.repository.padrao.ProblemaRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProblemaService
extends BaseService<ProblemaRequestDTO, ProblemaResponseDTO, Problema>{
    // mapper e repositories usando protected para uso em PriorizacaoProblemaService
    protected final ProblemaRepository repo;
    protected final ProblemaMapper mapper;
    private final CicloService cicloService;
    private final CausaRaizRepository causaRaizRepo;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("titulo", "descricao", "peso", "origem", "persistente", "idProblemaPai"),
            Set.of("titulo", "descricao", "peso")
    );

    public ProblemaService(ProblemaRepository repo, ProblemaMapper mapper, CicloService cicloService, CausaRaizRepository causaRaizRepo) {
        super(repo, mapper, Problema.class);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
        this.causaRaizRepo = causaRaizRepo;
    }

    @Override
    public ProblemaResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Problema problema = getEntity(id);
        Validador.validarCicloAberto(problema.getCiclo());
        Validador.validarProblemaAberto(problema);

        if (campos.containsKey("titulo")) problema.setTitulo((String) campos.get("titulo"));
        if (campos.containsKey("descricao")) problema.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("peso")) problema.setPeso((BigDecimal) campos.get("peso"));

        Problema salvo = repo.save(problema);
        return mapper.toResponse(salvo);
    }

    public ProblemaResponseDTO patchStatus(Long id, StatusProblema status){
        Problema problema = getEntity(id);

        if (!problema.getStatus().podeAtualizarStatus(status)) {
            throw new StatusUpdateException(problema.getStatus().toString(), status.toString());
        }

        switch (status) {
            case DESCARTADO, RESOLVIDO -> {
                validarSemPlanoExecucao(problema);
                atualizarStatusRecursivo(problema, status);
            }
            default -> problema.setStatus(status);
        }

        Problema salvo = repo.save(problema);
        return mapper.toResponse(salvo);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        Problema problema = getEntity(id);
        validarSemPlanoExecucao(problema);
        atualizarStatusRecursivo(problema, StatusProblema.DESCARTADO);
    }

    public ProblemaResponseDTO inserir(ProblemaRequestDTO dto, Long idCiclo) {
        Problema problema = mapper.toEntity(dto);
        Ciclo ciclo = cicloService.getEntity(idCiclo);

        Validador.validarCicloAberto(ciclo);
        problema.setCiclo(ciclo);

        if (dto.idProblemaPai() != null){
            Problema problemaPai = getEntity(dto.idProblemaPai());
            Validador.validarMesmoCiclo(problema.getCiclo(), problemaPai.getCiclo());
            problema.setProblemaPai(problemaPai);
        }

        Problema salvo = repo.save(problema);
        return mapper.toResponse(salvo);
    }

    public List<ProblemaResponseDTO> buscar(Long idCiclo, StatusProblema status, Long idProblemaPai){
        List<Problema> problemas;

        if (status == null && idProblemaPai == null){
            problemas = repo.findByCicloId(idCiclo);
        } else if (status != null && idProblemaPai == null) {
            problemas = repo.findByStatusAndCicloId(status, idCiclo);
        } else if (status == null){
            problemas = repo.findByCicloIdAndProblemaPaiId(idCiclo, idProblemaPai);
        } else {
            problemas = repo.findByStatusAndProblemaPaiIdAndCicloId(status, idProblemaPai, idCiclo);
        }

        verificarListaVazia(problemas);
        return mapper.toResponseList(problemas);
    }

    private void validarSemPlanoExecucao(Problema problema) {
        boolean temPlanoExecucao = causaRaizRepo.findByProblemaId(problema.getId()).stream()
                .anyMatch(causaRaiz -> causaRaiz.getPlanoAcao() != null && causaRaiz.getPlanoAcao().getStatus() == StatusPlanoAcao.EM_EXECUCAO);

        if (temPlanoExecucao) throw new BusinessRuleException("Não é possível excluir um problema se existe um plano de ação vinculado");
        problema.getSubProblemas().forEach(this::validarSemPlanoExecucao);
    }

    private void atualizarStatusRecursivo(Problema problema, StatusProblema status) {
        problema.setStatus(status);
        problema.getSubProblemas().forEach(subProblema -> atualizarStatusRecursivo(subProblema, status));
    }
}
