package br.com.acta.service;

import br.com.acta.dto.pdca.problema.ProblemaRequestDTO;
import br.com.acta.dto.pdca.problema.ProblemaResponseDTO;
import br.com.acta.entity.enums.StatusProblema;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.Problema;
import br.com.acta.mapper.pdca.ProblemaMapper;
import br.com.acta.repository.padrao.ProblemaRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.utils.PatchConfig;
import org.springframework.stereotype.Service;

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
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("titulo", "descricao", "peso", "origem", "persistente", "idProblemaPai"),
            Set.of("titulo", "descricao", "peso")
    );

    public ProblemaService(ProblemaRepository repo, ProblemaMapper mapper, CicloService cicloService) {
        super(repo, mapper, Problema.class);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
    }

    @Override
    public ProblemaResponseDTO patch(Long id, Map<String, Object> campos) {
        validarCampos(campos, patchConfig);
        Problema problema = getEntity(id);

        if (campos.containsKey("titulo")) problema.setTitulo((String) campos.get("titulo"));
        if (campos.containsKey("descricao")) problema.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("peso")) problema.setPeso((BigDecimal) campos.get("peso"));

        Problema salvo = repo.save(problema);
        return mapper.toResponse(salvo);
    }

    public ProblemaResponseDTO atualizarStatus(Long id, StatusProblema status){
        Problema problema = getEntity(id);
        problema.setStatus(status);

        Problema salvo = repo.save(problema);
        return mapper.toResponse(salvo);
    }

    @Override
    public void excluir(Long id) {
        Problema problema = getEntity(id);

        problema.setStatus(StatusProblema.DESCARTADO);
        problema.getSubProblemas().forEach(subProblema -> {
            subProblema.setStatus(StatusProblema.DESCARTADO);
            repo.save(subProblema);
        });

        repo.save(problema);
    }

    public ProblemaResponseDTO inserir(ProblemaRequestDTO dto, Long idCiclo) {
        Problema problema = mapper.toEntity(dto);
        Ciclo ciclo = cicloService.getEntity(idCiclo);

        problema.setCiclo(ciclo);
        antesInserir(problema, dto);

        Problema salvo = repo.save(problema);
        return mapper.toResponse(salvo);
    }

    @Override
    protected void antesInserir(Problema problema, ProblemaRequestDTO dto) {
        if (dto.idProblemaPai() != null){
            Problema problemaPai = getEntity(dto.idProblemaPai());

            if (!problemaPai.getCiclo().getId().equals(problema.getCiclo().getId())){
                throw new IllegalArgumentException("O problema pai deve pertencer ao mesmo ciclo do problema.");
            }
        }
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
}
