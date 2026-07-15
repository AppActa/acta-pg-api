package br.com.acta.service;

import br.com.acta.dto.pdca.causa_raiz.CausaRaizRequestDTO;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.entity.pdca.CausaRaiz;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.mapper.pdca.CausaRaizMapper;
import br.com.acta.repository.padrao.CausaRaizRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.utils.PatchConfig;
import br.com.acta.utils.Validador;
import org.springframework.stereotype.Service;

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

    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("descricao", "origem", "principal", "idProblema", "idCiclo", "idPlanoAcao", "id5PorquesMongo"),
            Set.of("descricao", "principal")
    );

    public CausaRaizService(CausaRaizRepository repo, CausaRaizMapper mapper, CicloService cicloService, PlanoAcaoService planoAcaoService, UsuarioService usuarioService) {
        super(repo, mapper, CausaRaiz.class);
        this.repo = repo;
        this.mapper = mapper;
        this.cicloService = cicloService;
        this.planoAcaoService = planoAcaoService;
        this.usuarioService = usuarioService;
    }

    @Override
    public CausaRaizResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        CausaRaiz causaRaiz = getEntity(id);

        if (campos.containsKey("descricao")) causaRaiz.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("principal")) causaRaiz.setPrincipal((Boolean) campos.get("principal"));

        CausaRaiz salvo = repo.save(causaRaiz);
        return mapper.toResponse(salvo);
    }

    public List<CausaRaizResponseDTO> buscar(Long idCiclo, Long idProblema, Boolean aceita, Boolean principal){
        List<CausaRaiz> causasRaiz = repo.buscar(idCiclo, idProblema, aceita, principal);
        verificarListaVazia(causasRaiz);

        return mapper.toResponseList(causasRaiz);
    }

    public CausaRaizResponseDTO inserir(CausaRaizRequestDTO dto, Long idCiclo) {
        CausaRaiz causaRaiz = mapper.toEntity(dto);
        Ciclo ciclo = cicloService.getEntity(idCiclo);

        causaRaiz.setCiclo(ciclo);
        CausaRaiz salvo = repo.save(causaRaiz);
        return mapper.toResponse(salvo);
    }

    public CausaRaizResponseDTO vincularPlanoAcao(Long idCausaRaiz, Long idPlanoAcao) {
        CausaRaiz causaRaiz = getEntity(idCausaRaiz);
        PlanoAcao planoAcao = planoAcaoService.getEntity(idPlanoAcao);

        Validador.validarMesmoCiclo(causaRaiz.getCiclo(), planoAcao.getCiclo());
        causaRaiz.setPlanoAcao(planoAcao);

        CausaRaiz salvo = repo.save(causaRaiz);
        return mapper.toResponse(salvo);
    }

    public CausaRaizResponseDTO validar(Long idCausaRaiz, Long idUsuario){
        CausaRaiz causaRaiz = getEntity(idCausaRaiz);
        Usuario usuario = usuarioService.getEntity(idUsuario);

        if (causaRaiz.getValidadaEm() != null){
            throw new IllegalStateException("A causa raiz já foi validada");
        }

        Validador.validarTipoUsuario(usuario, TipoUsuario.ADMIN, TipoUsuario.GESTOR);
        causaRaiz.setValidadaPor(usuario);
        causaRaiz.setValidadaEm(OffsetDateTime.now());

        CausaRaiz salvo = repo.save(causaRaiz);
        return mapper.toResponse(salvo);
    }
}
