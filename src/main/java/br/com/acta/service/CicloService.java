package br.com.acta.service;

import br.com.acta.common.handler.exception.StatusUpdateException;
import br.com.acta.dto.pdca.ciclo.CicloRequestDTO;
import br.com.acta.dto.pdca.ciclo.CicloResponseDTO;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.dto.mapper.pdca.CicloMapper;
import br.com.acta.repository.padrao.CicloRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CicloService
extends BaseService <CicloRequestDTO, CicloResponseDTO, Ciclo>{
    private final CicloRepository repo;
    private final CicloMapper mapper;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("titulo", "descricao", "dataInicio", "dataEstimadaFim", "idGestor", "idEmpresa"),
            Set.of("titulo", "descricao", "dataEstimadaFim")
    );

    public CicloService(CicloRepository repo, CicloMapper mapper) {
        super(repo, mapper, Ciclo.class);
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public CicloResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Ciclo ciclo = getEntity(id);
        Validador.validarCicloAberto(ciclo);

        if (campos.containsKey("titulo")) ciclo.setTitulo((String) campos.get("titulo"));
        if (campos.containsKey("descricao")) ciclo.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("dataEstimadaFim")) ciclo.setDataEstimadaFim((LocalDate) campos.get("dataEstimadaFim"));

        return mapper.toResponse(ciclo);
    }

    public CicloResponseDTO patchStatus(Long id, StatusCiclo status){
        Ciclo ciclo = getEntity(id);

        if (!ciclo.getStatus().podeAtualizarStatus(status)) {
            throw new StatusUpdateException(ciclo.getStatus().toString(), status.toString());
        }

        ciclo.setStatus(status);

        if (ciclo.getStatus().equals(StatusCiclo.CONCLUIDO)) {
            ciclo.setDataFimReal(LocalDate.now());
        }

        return mapper.toResponse(ciclo);
    }

    @Override
    public void excluir(Long id) {
        Ciclo ciclo = getEntity(id);

        Validador.validarCicloAberto(ciclo);
        ciclo.setStatus(StatusCiclo.CANCELADO);

        repo.save(ciclo);
    }

    public List<CicloResponseDTO> buscarPorGestor(Long idGestor){
        List<Ciclo> ciclos = repo.findByGestorId(idGestor);
        verificarListaVazia(ciclos);

        return mapper.toResponseList(ciclos);
    }

    public List<CicloResponseDTO> buscarPorEmpresa(Long idEmpresa){
        List<Ciclo> ciclos = repo.findByEmpresaId(idEmpresa);
        verificarListaVazia(ciclos);

        return mapper.toResponseList(ciclos);
    }

    public List<CicloResponseDTO> buscarPorStatus(Long idEmpresa, Long idGestor, StatusCiclo status){
        if (idEmpresa == null && idGestor == null) {
            throw new IllegalArgumentException("É necessário informar pelo menos o id da empresa ou do gestor.");
        }

        List<Ciclo> ciclos;

        if (idEmpresa != null && idGestor != null){
            ciclos = repo.findByEmpresaIdAndGestorIdAndStatus(idEmpresa, idGestor, status);
        } else if (idEmpresa != null) {
            ciclos = repo.findByEmpresaIdAndStatus(idEmpresa, status);
        } else {
            ciclos = repo.findByGestorIdAndStatus(idGestor, status);
        }

        verificarListaVazia(ciclos);
        return mapper.toResponseList(ciclos);
    }
}
