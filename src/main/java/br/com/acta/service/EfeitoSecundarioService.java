package br.com.acta.service;

import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioRequestDTO;
import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioResponseDTO;
import br.com.acta.entity.pdca.EfeitoSecundario;
import br.com.acta.entity.pdca.VerificacaoResultado;
import br.com.acta.handler.exception.ModelNotFoundException;
import br.com.acta.mapper.pdca.EfeitoSecundarioMapper;
import br.com.acta.repository.padrao.EfeitoSecundarioRepository;
import br.com.acta.utils.PatchConfig;
import br.com.acta.utils.Validador;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EfeitoSecundarioService {
    private final EfeitoSecundarioRepository repo;
    private final EfeitoSecundarioMapper mapper;
    private final VerificacaoResultadoService resultadoService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("descricao", "peso", "impactoEstimado", "tipo"),
            Set.of("descricao", "peso", "impactoEstimado")
    );

    public List<EfeitoSecundarioResponseDTO> buscar(Long idVerificacaoResultado){
        VerificacaoResultado resultado = resultadoService.getEntity(idVerificacaoResultado);
        List<EfeitoSecundario> efeitoSecundarios = repo.findByVerificacaoResultado(resultado);

        return mapper.toResponseList(efeitoSecundarios);
    }

    public EfeitoSecundarioResponseDTO inserir(Long idResultado, EfeitoSecundarioRequestDTO dto){
        VerificacaoResultado resultado = resultadoService.getEntity(idResultado);
        EfeitoSecundario efeitoSecundario = mapper.toEntity(dto);

        efeitoSecundario.setVerificacaoResultado(resultado);
        EfeitoSecundario salvo = repo.save(efeitoSecundario);
        return mapper.toResponse(salvo);
    }

    public EfeitoSecundarioResponseDTO patch(Long idResultado, Long idEfeitoSecundario, Map<String, Object> campos){
        Validador.validarCampos(campos, patchConfig);
        VerificacaoResultado verificacaoResultado = resultadoService.getEntity(idResultado);
        EfeitoSecundario efeitoSecundario = getEntity(idEfeitoSecundario);

        Validador.validarMesmoCiclo(verificacaoResultado.getCiclo(), efeitoSecundario.getVerificacaoResultado().getCiclo());

        if ( campos.containsKey("descricao")) efeitoSecundario.setDescricao(((String) campos.get("descricao")));
        if ( campos.containsKey("peso")) efeitoSecundario.setPeso(((BigDecimal) campos.get("peso")));
        if ( campos.containsKey("impactoEstimado")) efeitoSecundario.setImpactoEstimado(((String) campos.get("impactoEstimado")));

        EfeitoSecundario salvo = repo.save(efeitoSecundario);
        return mapper.toResponse(salvo);
    }

    public void excluir(Long idResultado, Long idEfeitoSecundario) {
        VerificacaoResultado verificacaoResultado = resultadoService.getEntity(idResultado);
        EfeitoSecundario efeitoSecundario = getEntity(idEfeitoSecundario);

        Validador.validarMesmoCiclo(verificacaoResultado.getCiclo(), efeitoSecundario.getVerificacaoResultado().getCiclo());

        repo.delete(efeitoSecundario);
    }

    private EfeitoSecundario getEntity(Long id) {
        return repo.findById(id).orElseThrow(() -> new ModelNotFoundException("Efeito Secundário", id));
    }
}
