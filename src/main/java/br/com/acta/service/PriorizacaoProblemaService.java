package br.com.acta.service;

import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaRequestDTO;
import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaResponseDTO;
import br.com.acta.dto.mapper.join.PriorizacaoProblemaMapper;
import br.com.acta.dto.mapper.pdca.ProblemaMapper;
import br.com.acta.dto.pdca.problema.ProblemaResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.join.PriorizacaoProblema;
import br.com.acta.entity.join.id.PriorizacaoProblemaId;
import br.com.acta.entity.pdca.Problema;
import br.com.acta.repository.composto.PriorizacaoProblemaRepository;
import br.com.acta.repository.padrao.ProblemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PriorizacaoProblemaService {
    private final ProblemaService problemaService;
    private final UsuarioService usuarioService;
    private final PriorizacaoProblemaRepository repo;
    private final PriorizacaoProblemaMapper mapper;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("idUsuario", "posicao", "pesoCalculado", "idProblema"),
            Set.of("posicao", "pesoCalculado")
    );
    private final ProblemaRepository problemaRepo;
    private final ProblemaMapper problemaMapper;

    protected PriorizacaoProblema getEntity(Long idProblema, Long idUsuario){
        PriorizacaoProblemaId id = new PriorizacaoProblemaId(idProblema, idUsuario);

        return repo.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("PriorizacaoProblema", List.of(idProblema, idUsuario)));
    }

    @Transactional
    public PriorizacaoProblemaResponseDTO inserir(Long idProblema, PriorizacaoProblemaRequestDTO dto){
        Problema problema = problemaService.getEntity(idProblema);
        Usuario usuario = usuarioService.getEntity(dto.idUsuario());

        Validador.validarMesmoCiclo(problema.getCiclo(), usuario.getCiclos());

        List<PriorizacaoProblema> respostasUsuario = repo.findByProblemaAndUsuario(problema, usuario);
        if (!respostasUsuario.isEmpty()) {
            throw new UniqueViolationException("Problema", "Usuário");
        }

        PriorizacaoProblema priorizacaoProblema = mapper.toEntity(dto);
        priorizacaoProblema.setProblema(problema);
        priorizacaoProblema.setUsuario(usuario);

        // proteção contra race condition
        try {
            PriorizacaoProblema salvo = repo.saveAndFlush(priorizacaoProblema);
            return mapper.toResponse(salvo);
        } catch (DataIntegrityViolationException dive){
            throw new UniqueViolationException("Problema", "Usuário");
        }
    }

    @Transactional
    public PriorizacaoProblemaResponseDTO patch(Long idProblema, Long idUsuario, Map<String, Object> campos){
        Validador.validarCampos(campos, patchConfig);
        PriorizacaoProblema priorizacaoProblema = getEntity(idProblema, idUsuario);

        if (campos.containsKey("posicao")) priorizacaoProblema.setPosicao((Integer) campos.get("posicao"));
        if (campos.containsKey("pesoCalculado")) priorizacaoProblema.setPesoCalculado((BigDecimal) campos.get("pesoCalculado"));

        PriorizacaoProblema salvo = repo.save(priorizacaoProblema);
        return mapper.toResponse(salvo);
    }

    @Transactional(readOnly = true)
    public List<PriorizacaoProblemaResponseDTO> buscar(Long idProblema, Long idUsuario){
        List<PriorizacaoProblema> priorizacoes;
        Problema problema = problemaService.getEntity(idProblema);

        if (idUsuario == null ) {
            priorizacoes = repo.findByProblema(problema);
        } else {
            Usuario usuario = usuarioService.getEntity(idUsuario);
            Validador.validarMesmoCiclo(problema.getCiclo(), usuario.getCiclos());
            priorizacoes = repo.findByProblemaAndUsuario(problema, usuario);
        }

        return mapper.toResponseList(priorizacoes);
    }

    @Transactional
    public ProblemaResponseDTO aplicarPeso(Long idProblema){
        Problema problema = problemaService.getEntity(idProblema);
        List<PriorizacaoProblema> priorizacoes = repo.findByProblema(problema);

        if (priorizacoes.isEmpty()) {
            throw new ModelNotFoundException("Priorização de problema");
        }

        BigDecimal soma = BigDecimal.ZERO;
        for (PriorizacaoProblema priorizacaoProblema : priorizacoes) {
            soma = soma.add(priorizacaoProblema.getPesoCalculado());
        }
        BigDecimal peso = soma.divide(new BigDecimal(priorizacoes.size()), 2, RoundingMode.HALF_UP);

        problema.setPeso(peso);
        Problema salvo = problemaRepo.save(problema);
        return problemaMapper.toResponse(salvo);
    }
}
