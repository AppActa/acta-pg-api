package br.com.acta.service;

import br.com.acta.common.handler.exception.ActiveEntityDeletionException;
import br.com.acta.common.handler.exception.InvalidRequestException;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.mapper.pdca.TreinamentoMapper;
import br.com.acta.dto.pdca.treinamento.TreinamentoRequestDTO;
import br.com.acta.dto.pdca.treinamento.TreinamentoResponseDTO;
import br.com.acta.entity.enums.StatusTreinamento;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.Treinamento;
import br.com.acta.repository.padrao.TreinamentoRepository;
import br.com.acta.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class TreinamentoService
extends BaseService<TreinamentoRequestDTO, TreinamentoResponseDTO, Treinamento> {
    private final TreinamentoRepository repo;
    private final TreinamentoMapper mapper;
    private final CicloService cicloService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("titulo", "descricao", "dataTreinamento", "obrigatorio", "idAnexoMongo", "idCiclo", "idResponsavel"),
            Set.of("titulo", "descricao", "dataTreinamento", "obrigatorio")
    );

    public TreinamentoService(TreinamentoRepository repo, TreinamentoMapper mapper, CicloService cicloService){
        super(repo, mapper, Treinamento.class);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
    }

    @Override
    public TreinamentoResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Treinamento treinamento = getEntity(id);

        if (campos.containsKey("titulo")) treinamento.setTitulo((String) campos.get("titulo"));
        if (campos.containsKey("descricao")) treinamento.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("dataTreinamento")) {
            LocalDate dataTreinamento = (LocalDate) campos.get("dataTreinamento");
            if (dataTreinamento.isBefore(LocalDate.now()))
                throw new InvalidRequestException("A data do treinamento não pode ser anterior à data atual");
            treinamento.setDataTreinamento(dataTreinamento);
        }
        if (campos.containsKey("obrigatorio")) treinamento.setObrigatorio((Boolean) campos.get("obrigatorio"));

        Treinamento salvo = repo.save(treinamento);
        return mapper.toResponse(salvo);
    }

    public List<TreinamentoResponseDTO> buscarTreinamentos(Long idCiclo){
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        List<Treinamento> treinamentos = repo.findByCiclo(ciclo);

        return mapper.toResponseList(treinamentos);
    }

    public TreinamentoResponseDTO inserir(Long idCiclo, TreinamentoRequestDTO dto) {
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        Validador.validarCicloAberto(ciclo);

        Treinamento treinamento = mapper.toEntity(dto);

        treinamento.setCiclo(ciclo);
        Treinamento salvo = repo.save(treinamento);
        return mapper.toResponse(salvo);
    }

    @Override
    public void excluir(Long id) {
        Treinamento treinamento = getEntity(id);
        Validador.validarCicloAberto(treinamento.getCiclo());

        if (treinamento.getDataTreinamento().isBefore(LocalDate.now())) {
            throw new InvalidRequestException("Não é possível excluir treinamentos que já foram terminados");
        }

        treinamento.getParticipantes().forEach(participante -> {
            if (participante.getStatus() == StatusTreinamento.CONCLUIDO){
                throw new ActiveEntityDeletionException("Treinamento");
            }
        });

        repo.delete(treinamento);
    }

}
