package br.com.acta.service;

import br.com.acta.common.handler.exception.InvalidResourceStatusException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.common.utils.ConversorObject;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class Plano5W2HService
extends BaseService<Plano5W2HRequestDTO, Plano5W2HResponseDTO, Plano5W2H> {
    private final Plano5W2HRepository repo;
    private final Plano5W2HMapper mapper;
    private final PlanoAcaoService planoAcaoService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("whatAcao", "whyJustificativa", "whereLocal", "whenInicio", "whenFim", "howModoExecucao", "howMuchCusto", "idWhoResponsavel", "idPlanoAcao"),
            Set.of("whatAcao", "whyJustificativa", "whereLocal", "whenInicio", "whenFim", "howModoExecucao", "howMuchCusto", "idWhoResponsavel")
    );
    private final UsuarioService usuarioService;

    public Plano5W2HService(Plano5W2HRepository repo, Plano5W2HMapper mapper, PlanoAcaoService planoAcaoService, UsuarioService usuarioService, AuthService authService) {
        super(repo, mapper, Plano5W2H.class, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.planoAcaoService = planoAcaoService;
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public Plano5W2HResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Plano5W2H plano5W2H = getEntity(id);
        PlanoAcao planoAcao = plano5W2H.getPlanoAcao();

        if (planoAcao != null && Set.of(StatusPlanoAcao.CANCELADO, StatusPlanoAcao.CONCLUIDO).contains(planoAcao.getStatus())) {
            throw new InvalidResourceStatusException("5W2H", List.of(StatusPlanoAcao.CANCELADO.toString(), StatusPlanoAcao.CONCLUIDO.toString()));
        }

        if (campos.containsKey("whatAcao")) plano5W2H.setWhatAcao((String) campos.get("whatAcao"));
        if (campos.containsKey("whyJustificativa")) plano5W2H.setWhyJustificativa((String) campos.get("whyJustificativa"));
        if (campos.containsKey("whereLocal")) plano5W2H.setWhereLocal((String) campos.get("whereLocal"));
        if (campos.containsKey("howModoExecucao")) plano5W2H.setHowModoExecucao((String) campos.get("howModoExecucao"));

        if (campos.containsKey("whenInicio")) {
            Object whenInicioObject = campos.get("whenInicio");
            plano5W2H.setWhenInicio(ConversorObject.toLocalDate(whenInicioObject));
        }

        if (campos.containsKey("whenFim")) {
            Object whenFimObject = campos.get("whenFim");
            plano5W2H.setWhenFim(ConversorObject.toLocalDate(whenFimObject, false));
        }

        if (campos.containsKey("howMuchCusto")) {
            Object howMuchCustoObject = campos.get("howMuchCusto");
            plano5W2H.setHowMuchCusto(ConversorObject.toBigDecimal(howMuchCustoObject));
        }

        if (campos.containsKey("idWhoResponsavel")){
            Object idWhoResponsavelObject = campos.get("idWhoResponsavel");
            Long idWhoResponsavel = ConversorObject.toLong(idWhoResponsavelObject);
            Usuario responsavel = usuarioService.getEntity(idWhoResponsavel);

            Validador.validarMesmoCiclo(plano5W2H.getPlanoAcao().getCiclo(), responsavel.getCiclos());
            plano5W2H.setWhoResponsavel(responsavel);
        }

        Plano5W2H salvo = repo.save(plano5W2H);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public Plano5W2HResponseDTO inserir(Plano5W2HRequestDTO dto, Long idPlanoAcao) {
        Plano5W2H plano5W2H = mapper.toEntity(dto);
        PlanoAcao planoAcao = planoAcaoService.getEntity(idPlanoAcao);

        if (planoAcao.getPlano5W2H() != null) {
            throw new UniqueViolationException("Plano de Ação", "5W2H");
        }

        if (Set.of(StatusPlanoAcao.CANCELADO, StatusPlanoAcao.CONCLUIDO).contains(planoAcao.getStatus())) {
            throw new InvalidResourceStatusException("5W2H", List.of(StatusPlanoAcao.CANCELADO.toString(), StatusPlanoAcao.CONCLUIDO.toString()));
        }

        Usuario usuario = usuarioService.getEntity(dto.idWhoResponsavel());
        Validador.validarMesmoCiclo(planoAcao.getCiclo(), usuario.getCiclos());

        plano5W2H.setWhoResponsavel(usuario);
        plano5W2H.setPlanoAcao(planoAcao);
        Plano5W2H salvo = repo.save(plano5W2H);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional
    public Plano5W2HResponseDTO buscarPorPlanoAcao(Long idPlanoAcao){
        Plano5W2H plano5W2H = repo.findByPlanoAcaoId(idPlanoAcao).orElseThrow(() -> new ModelNotFoundException("5W2H"));
        return mapper.toResponse(plano5W2H);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public void excluir(Long id) {
        Plano5W2H plano5W2H = getEntity(id);
        PlanoAcao planoAcao = plano5W2H.getPlanoAcao();

        if (Set.of(StatusPlanoAcao.CONCLUIDO, StatusPlanoAcao.CANCELADO).contains(plano5W2H.getPlanoAcao().getStatus())){
            throw new InvalidResourceStatusException("5W2H", List.of(StatusPlanoAcao.CANCELADO.toString(), StatusPlanoAcao.CONCLUIDO.toString()));
        }

        planoAcao.setPlano5W2H(null);
        repo.delete(plano5W2H);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public Plano5W2HResponseDTO buscar(Long id) {
        return super.buscar(id);
    }
}
