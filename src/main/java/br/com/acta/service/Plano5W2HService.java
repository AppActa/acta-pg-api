package br.com.acta.service;

import br.com.acta.common.handler.exception.InvalidResourceStatusException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.mapper.pdca.Plano5W2HMapper;
import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HRequestDTO;
import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusPlanoAcao;
import br.com.acta.entity.pdca.Plano5W2H;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.repository.padrao.Plano5W2HRepository;
import br.com.acta.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class Plano5W2HService
extends BaseService<Plano5W2HRequestDTO, Plano5W2HResponseDTO, Plano5W2H> {
    private final Plano5W2HRepository repo;
    private final Plano5W2HMapper mapper;
    private final PlanoAcaoService planoAcaoService;
    private final UsuarioCicloService usuarioCicloService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("whatAcao", "whyJustificativa", "whereLocal", "whenInicio", "whenFim", "howModoExecucao", "howMuchCusto", "idWhoResponsavel", "idPlanoAcao"),
            Set.of("whatAcao", "whyJustificativa", "whereLocal", "whenInicio", "whenFim", "howModoExecucao", "howMuchCusto", "idWhoResponsavel")
    );

    public Plano5W2HService(Plano5W2HRepository repo, Plano5W2HMapper mapper, PlanoAcaoService planoAcaoService, UsuarioCicloService usuarioCicloService) {
        super(repo, mapper, Plano5W2H.class);
        this.repo = repo;
        this.mapper = mapper;
        this.planoAcaoService = planoAcaoService;
        this.usuarioCicloService = usuarioCicloService;
    }

    @Override
    public Plano5W2HResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Plano5W2H plano5W2H = getEntity(id);
        PlanoAcao planoAcao = plano5W2H.getPlanoAcao();

        if (planoAcao != null && !Set.of(StatusPlanoAcao.RASCUNHO, StatusPlanoAcao.APROVADO).contains(planoAcao.getStatus())) {
            throw new InvalidResourceStatusException("5W2H", List.of(StatusPlanoAcao.RASCUNHO.toString(), StatusPlanoAcao.APROVADO.toString()));
        }

        if (campos.containsKey("whatAcao")) plano5W2H.setWhatAcao((String) campos.get("whatAcao"));
        if (campos.containsKey("whyJustificativa")) plano5W2H.setWhyJustificativa((String) campos.get("whyJustificativa"));
        if (campos.containsKey("whereLocal")) plano5W2H.setWhereLocal((String) campos.get("whereLocal"));
        if (campos.containsKey("whenInicio")) plano5W2H.setWhenInicio((LocalDate) campos.get("whenInicio"));
        if (campos.containsKey("whenFim")) plano5W2H.setWhenFim((LocalDate) campos.get("whenFim"));
        if (campos.containsKey("howModoExecucao")) plano5W2H.setHowModoExecucao((String) campos.get("howModoExecucao"));
        if (campos.containsKey("howMuchCusto")) plano5W2H.setHowMuchCusto((BigDecimal) campos.get("howMuchCusto"));
        if (campos.containsKey("idWhoResponsavel")){
            Long idWhoResponsavel = (Long) campos.get("idWhoResponsavel");
            Usuario responsavel = usuarioCicloService.usuarioService.getEntity(idWhoResponsavel);

            Validador.validarMesmoCiclo(plano5W2H.getPlanoAcao().getCiclo(), responsavel.getCiclos());

            plano5W2H.setWhoResponsavel(responsavel);
        }

        Plano5W2H salvo = repo.save(plano5W2H);
        return mapper.toResponse(salvo);
    }

    public Plano5W2HResponseDTO inserir(Plano5W2HRequestDTO dto, Long idPlanoAcao) {
        Plano5W2H plano5W2H = mapper.toEntity(dto);
        PlanoAcao planoAcao = planoAcaoService.getEntity(idPlanoAcao);

        if (planoAcao.getStatus() != StatusPlanoAcao.RASCUNHO) {
            throw new InvalidResourceStatusException("inserir", "5W2H", StatusPlanoAcao.RASCUNHO.toString());
        }

        Usuario usuario = usuarioCicloService.usuarioService.getEntity(dto.idWhoResponsavel());
        Validador.validarMesmoCiclo(planoAcao.getCiclo(), usuario.getCiclos());

        plano5W2H.setPlanoAcao(planoAcao);
        Plano5W2H salvo = repo.save(plano5W2H);
        return mapper.toResponse(salvo);
    }

    public Plano5W2HResponseDTO buscarPorPlanoAcao(Long idPlanoAcao){
        Plano5W2H plano5W2H = repo.findByPlanoAcaoId(idPlanoAcao).orElseThrow(() -> new ModelNotFoundException("5W2H"));
        return mapper.toResponse(plano5W2H);
    }

    @Override
    public void excluir(Long id) {
        Plano5W2H plano5W2H = getEntity(id);
        if (Set.of(StatusPlanoAcao.CONCLUIDO, StatusPlanoAcao.EM_EXECUCAO)
                .contains(plano5W2H.getPlanoAcao().getStatus())
        ){
            throw new InvalidResourceStatusException("5W2H", List.of(StatusPlanoAcao.RASCUNHO.toString(), StatusPlanoAcao.APROVADO.toString()));
        }

        repo.delete(plano5W2H);
    }
}
