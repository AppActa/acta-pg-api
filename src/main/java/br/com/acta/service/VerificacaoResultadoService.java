package br.com.acta.service;

import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoRequestDTO;
import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoResponseDTO;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.VerificacaoResultado;
import br.com.acta.common.handler.exception.BusinessRuleException;
import br.com.acta.dto.mapper.pdca.VerificacaoResultadoMapper;
import br.com.acta.repository.padrao.VerificacaoResultadoRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class VerificacaoResultadoService extends BaseService<VerificacaoResultadoRequestDTO, VerificacaoResultadoResponseDTO, VerificacaoResultado> {
    private final VerificacaoResultadoRepository repo;
    private final VerificacaoResultadoMapper mapper;
    private final CicloService cicloService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("status", "idCiclo", "resumo", "observacao"),
            Set.of("resumo", "observacao")
    );

    public VerificacaoResultadoService(VerificacaoResultadoRepository repo, VerificacaoResultadoMapper mapper, CicloService cicloService) {
        super(repo, mapper, VerificacaoResultado.class);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
    }

    @Override
    public VerificacaoResultadoResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        VerificacaoResultado resultado = getEntity(id);

        if (campos.containsKey("resumo")) resultado.setResumo((String) campos.get("resumo"));
        if (campos.containsKey("observacao")) resultado.setObservacao((String) campos.get("observacao"));

        VerificacaoResultado salvo = repo.save(resultado);
        return mapper.toResponse(salvo);
    }

    public List<VerificacaoResultadoResponseDTO> buscarVerificacoes(Long idCiclo){
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        List<VerificacaoResultado> resultados = repo.findByCiclo(ciclo);

        return mapper.toResponseList(resultados);
    }

    public VerificacaoResultadoResponseDTO inserir(Long idCiclo, VerificacaoResultadoRequestDTO dto){
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        VerificacaoResultado resultado = mapper.toEntity(dto);

        resultado.setCiclo(ciclo);
        VerificacaoResultado salvo = repo.save(resultado);
        return mapper.toResponse(salvo);
    }

    @Override
    public void excluir(Long id) {
        VerificacaoResultado resultado = getEntity(id);

        // todo validar todos os outros que não podem ser
        if (resultado.getCiclo().getStatus() != StatusCiclo.VERIFICACAO) {
            throw new BusinessRuleException("Não é possível excluir a verificação do resultado de um ciclo que já avançou de etapa");
        }

        repo.delete(resultado);
    }
}
