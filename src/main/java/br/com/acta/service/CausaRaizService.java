package br.com.acta.service;

import br.com.acta.common.handler.exception.InvalidRequestException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.mapper.pdca.CausaRaizMapper;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizRequestDTO;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.entity.pdca.CausaRaiz;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.entity.pdca.Problema;
import br.com.acta.repository.padrao.CausaRaizRepository;
import br.com.acta.service.base.BaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CausaRaizService
extends BaseService<CausaRaizRequestDTO, CausaRaizResponseDTO, CausaRaiz> {
    private final CausaRaizRepository repo;
    private final CausaRaizMapper mapper;

    private final CicloService cicloService;
    private final PlanoAcaoService planoAcaoService;
    private final UsuarioService usuarioService;
    private final ProblemaService problemaService;

    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("descricao", "origem", "principal", "idProblema", "idCiclo", "idPlanoAcao", "id5PorquesMongo"),
            Set.of("descricao", "principal", "id5PorquesMongo")
    );

    public CausaRaizService(CausaRaizRepository repo, CausaRaizMapper mapper, CicloService cicloService, PlanoAcaoService planoAcaoService, UsuarioService usuarioService, ProblemaService problemaService, AuthService authService) {
        super(repo, mapper, CausaRaiz.class, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
        this.planoAcaoService = planoAcaoService;
        this.usuarioService = usuarioService;
        this.problemaService = problemaService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public CausaRaizResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        CausaRaiz causaRaiz = getEntity(id);

        if (campos.containsKey("descricao")) causaRaiz.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("principal")) causaRaiz.setPrincipal((Boolean) campos.get("principal"));
        if (campos.containsKey("id5PorquesMongo")) causaRaiz.setId5PorquesMongo((String) campos.get("id5PorquesMongo"));

        CausaRaiz salvo = repo.save(causaRaiz);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<CausaRaizResponseDTO> buscar(Long idCiclo, Long idProblema, Boolean aceita, Boolean principal){
        List<CausaRaiz> causasRaiz = repo.buscar(idCiclo, idProblema, aceita, principal);

        return mapper.toResponseList(causasRaiz);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public CausaRaizResponseDTO inserir(CausaRaizRequestDTO dto, Long idCiclo) {
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        Validador.validarCicloAberto(ciclo);

        Problema problema = problemaService.getEntity(dto.idProblema());
        Validador.validarMesmoCiclo(ciclo, problema.getCiclo());

        CausaRaiz causaRaiz = mapper.toEntity(dto);
        causaRaiz.setCiclo(ciclo);
        causaRaiz.setProblema(problema);
        causaRaiz.setAceita(false);

        CausaRaiz salvo = repo.save(causaRaiz);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public CausaRaizResponseDTO vincularPlanoAcao(Long idCausaRaiz, Long idPlanoAcao) {
        CausaRaiz causaRaiz = getEntity(idCausaRaiz);

        if (causaRaiz.getPlanoAcao() != null){
            throw new UniqueViolationException("Causa raiz", "Plano de ação");
        }

        PlanoAcao planoAcao = planoAcaoService.getEntity(idPlanoAcao);
        Validador.validarMesmoCiclo(causaRaiz.getCiclo(), planoAcao.getCiclo());
        if (causaRaiz.getAceita() == null || !causaRaiz.getAceita()) {
            throw new InvalidRequestException("A causa raiz não está aceita para vinculação com plano de ação");
        }
        causaRaiz.setPlanoAcao(planoAcao);

        CausaRaiz salvo = repo.save(causaRaiz);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public CausaRaizResponseDTO validar(Long idCausaRaiz, Long idUsuario, Boolean aceita){
        CausaRaiz causaRaiz = getEntity(idCausaRaiz);
        Usuario usuario = usuarioService.getEntity(idUsuario);

        if (causaRaiz.getValidadaEm() != null){
            throw new InvalidRequestException("A causa raiz já foi validada");
        }

        Validador.validarTipoUsuario(usuario, TipoUsuario.ADMIN, TipoUsuario.GESTOR);
        Validador.validarMesmoCiclo(causaRaiz.getCiclo(), usuario.getCiclos());

        causaRaiz.setAceita(aceita);
        causaRaiz.setValidadaPor(usuario);
        causaRaiz.setValidadaEm(OffsetDateTime.now());

        CausaRaiz salvo = repo.save(causaRaiz);
        return mapper.toResponse(salvo);
    }
}
