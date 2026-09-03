package br.com.acta.service;

import br.com.acta.common.handler.exception.InvalidResourceStatusException;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.mapper.pdca.VerificacaoResultadoMapper;
import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoRequestDTO;
import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.VerificacaoResultado;
import br.com.acta.repository.padrao.VerificacaoResultadoRepository;
import br.com.acta.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final UsuarioService usuarioService;

    public VerificacaoResultadoService(VerificacaoResultadoRepository repo, VerificacaoResultadoMapper mapper, CicloService cicloService, UsuarioService usuarioService, AuthService authService) {
        super(repo, mapper, VerificacaoResultado.class, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
        this.usuarioService = usuarioService;
    }

    @Transactional
    @Override
    public VerificacaoResultadoResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        VerificacaoResultado resultado = getEntity(id);

        if (resultado.getCiclo().getStatus() != StatusCiclo.VERIFICACAO) {
            throw new InvalidResourceStatusException("atualizar", "Verificação de Resultado", StatusCiclo.VERIFICACAO.toString());
        }

        if (campos.containsKey("resumo")) resultado.setResumo((String) campos.get("resumo"));
        if (campos.containsKey("observacao")) resultado.setObservacao((String) campos.get("observacao"));

        VerificacaoResultado salvo = repo.save(resultado);
        return mapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public List<VerificacaoResultadoResponseDTO> buscarVerificacoes(Long idCiclo){
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        List<VerificacaoResultado> resultados = repo.findByCiclo(ciclo);

        return mapper.toResponseList(resultados);
    }

    @Transactional
    public VerificacaoResultadoResponseDTO inserir(Long idCiclo, VerificacaoResultadoRequestDTO dto, Long idCriadoPor){
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        Usuario usuario = usuarioService.getEntity(idCriadoPor);
        Validador.validarCicloAberto(ciclo);

        if (ciclo.getStatus() != StatusCiclo.VERIFICACAO) {
            throw new InvalidResourceStatusException("inserir", "Verificação de Resultado", StatusCiclo.VERIFICACAO.toString(), "Ciclo");
        }

        VerificacaoResultado resultado = mapper.toEntity(dto);
        resultado.setCiclo(ciclo);
        resultado.setCriadoPor(usuario);

        VerificacaoResultado salvo = repo.save(resultado);
        return mapper.toResponse(salvo);
    }

    @Transactional
    @Override
    public void excluir(Long id) {
        VerificacaoResultado resultado = getEntity(id);

        if (resultado.getCiclo().getStatus() != StatusCiclo.VERIFICACAO) {
            throw new InvalidResourceStatusException("excluir", "Verificação de Resultado", StatusCiclo.VERIFICACAO.toString());
        }

        repo.delete(resultado);
    }
}
