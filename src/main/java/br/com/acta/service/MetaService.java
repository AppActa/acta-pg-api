package br.com.acta.service;

import br.com.acta.dto.core.usuario.UsuarioSummaryResponseDTO;
import br.com.acta.dto.pdca.meta.MetaRequestDTO;
import br.com.acta.dto.pdca.meta.MetaResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusMeta;
import br.com.acta.entity.pdca.Meta;
import br.com.acta.mapper.pdca.MetaMapper;
import br.com.acta.repository.padrao.MetaRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.utils.PatchConfig;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MetaService
extends BaseService<MetaRequestDTO, MetaResponseDTO, Meta> {
    private final MetaRepository repo;
    private final MetaMapper mapper;
    private final UsuarioService usuarioService;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("objetivo", "responsaveis", "status", "prioridade", "valorBase", "valorAlvo", "unidadeMedida", "prazo", "area", "categoria"),
            Set.of("objetivo", "valorAlvo", "prazo", "prioridade", "area", "categoria")
    );

    public MetaService(MetaRepository repo, MetaMapper mapper, UsuarioService usuarioService) {
        super(repo, mapper, Meta.class);
        this.repo = repo;
        this.mapper = mapper;
        this.usuarioService = usuarioService;
    }

    @Override
    public MetaResponseDTO patch(Long id, Map<String, Object> campos) {
        validarCampos(campos, patchConfig);
        Meta meta = getEntity(id);

        if(campos.containsKey("objetivo")) meta.setObjetivo((String) campos.get("objetivo"));
        if(campos.containsKey("valorAlvo")) meta.setValorAlvo((BigDecimal) campos.get("valorAlvo"));
        if(campos.containsKey("prazo")) meta.setPrazo((LocalDate) campos.get("prazo"));
        if(campos.containsKey("prioridade")) meta.setPrioridade((Prioridade) campos.get("prioridade"));
        if(campos.containsKey("area")) meta.setArea((String) campos.get("area"));
        if(campos.containsKey("categoria")) meta.setCategoria((String) campos.get("categoria"));

        Meta salvo = repo.save(meta);
        return mapper.toResponse(salvo);
    }

    public MetaResponseDTO atualizarStatus(Long id, StatusMeta status){
        Meta meta = getEntity(id);

        meta.setStatus(status);

        Meta salvo = repo.save(meta);
        return mapper.toResponse(salvo);
    }

    @Override
    public void excluir(Long id) {
        Meta meta = getEntity(id);

        // todo verificar se não tem dependentes ativos
        meta.setStatus(StatusMeta.CANCELADA);
        repo.save(meta);
    }

    public List<MetaResponseDTO> buscar(Long idCiclo, StatusMeta status, Prioridade prioridade) {
        List<Meta> metas;

        if (status == null && prioridade == null){
            metas = repo.findByCicloId(idCiclo);
        } else if (status == null){
            metas = repo.findByCicloIdAndPrioridade(idCiclo, prioridade);
        } else if (prioridade == null){
            metas = repo.findByCicloIdAndStatus(idCiclo, status);
        } else {
            metas = repo.findByCicloIdAndStatusAndPrioridade(idCiclo, status, prioridade);
        }

        verificarListaVazia(metas);
        return mapper.toResponseList(metas);
    }

    public List<UsuarioSummaryResponseDTO> buscarResponsaveis(Long id){
        Meta meta = getEntity(id);
        Set<Usuario> responsaveis = meta.getResponsaveis();

        return usuarioService.mapper.toSummaryList(responsaveis);
    }

    public List<UsuarioSummaryResponseDTO> inserirResponsaveis(Long idMeta, List<Long> idResponsaveis){
        Meta meta = getEntity(idMeta);
        Set<Usuario> responsaveisAtuais = meta.getResponsaveis();

        // todo otimizar futuramente
        for (Long idResponsavel : idResponsaveis) {
            for (Usuario responsavel : responsaveisAtuais) {
                if (responsavel.getId().equals(idResponsavel)) {
                    throw new IllegalArgumentException("Responsável com o id " + idResponsavel + " já está atribuído para esta meta");
                }
            }

            Usuario usuario = usuarioService.getEntity(idResponsavel);
            responsaveisAtuais.add(usuario);
        }

        repo.save(meta);
        return usuarioService.mapper.toSummaryList(responsaveisAtuais);
    }

    public void excluirResponsaveis(Long idMeta, List<Long> idResponsaveis){
        Meta meta = getEntity(idMeta);
        Set<Usuario> responsaveisAtuais = meta.getResponsaveis();

        for (Long idResponsavel : idResponsaveis) {
            responsaveisAtuais.removeIf(usuario -> usuario.getId().equals(idResponsavel));
        }

        repo.save(meta);
    }
}
