package br.com.acta.service;

import br.com.acta.common.handler.exception.ActiveEntityDeletionException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioResponseDTO;
import br.com.acta.dto.mapper.core.UsuarioMapper;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.*;
import br.com.acta.repository.padrao.MetaRepository;
import br.com.acta.repository.padrao.TarefaRepository;
import br.com.acta.repository.padrao.UsuarioRepository;
import br.com.acta.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UsuarioService
extends BaseService<UsuarioRequestDTO, UsuarioResponseDTO, Usuario> {
    private final EmpresaService empresaService;
    private final TarefaRepository tarefaRepo;
    private final MetaRepository metaRepo;
    private final UsuarioRepository repo;
    protected final UsuarioMapper mapper;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("nome", "email", "firebaseUid", "tipo", "idEmpresa"),
            Set.of("nome", "email", "firebaseUid")
    );

    public UsuarioService(EmpresaService empresaService, UsuarioRepository repo, UsuarioMapper mapper, TarefaRepository tarefaRepo, MetaRepository metaRepo){
        super(repo, mapper, Usuario.class);
        this.empresaService = empresaService;
        this.repo = repo;
        this.mapper = mapper;
        this.tarefaRepo = tarefaRepo;
        this.metaRepo = metaRepo;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Usuario usuario = getEntity(id);

        if (campos.containsKey("nome")) usuario.setNome((String) campos.get("nome"));
        if (campos.containsKey("email")) {
            String email = (String) campos.get("email");

            if (repo.existsByEmailLoginIgnoreCase(email)) throw new UniqueViolationException("E-mail");
            usuario.setEmailLogin(email);
        }

        repo.save(usuario);
        return mapper.toResponse(usuario);
    }

    public List<UsuarioResponseDTO> buscar(Long idEmpresa, TipoUsuario tipo) {
        List<Usuario> usuarios;

        if (tipo == null) usuarios = repo.findByEmpresaIdAndStatus(idEmpresa, StatusGeral.ATIVO);
        else usuarios = repo.findByTipoAndStatusAndEmpresaId(tipo, StatusGeral.ATIVO, idEmpresa);

        return mapper.toResponseList(usuarios);
    }

    @Override
    public void excluir(Long id) {
        Usuario usuario = getEntity(id);

        boolean gestorCicloAtivo = usuario.getCiclos().stream()
                        .anyMatch(usuarioCiclo ->
                            usuarioCiclo.getPapelCiclo() == PapelCiclo.RESPONSAVEL &&
                                    usuarioCiclo.getCiclo().getStatus() != StatusCiclo.CANCELADO && usuarioCiclo.getCiclo().getStatus() != StatusCiclo.CONCLUIDO
                        );

        if (gestorCicloAtivo) {
            throw new ActiveEntityDeletionException("Usuário");
        }

        boolean responsavelTarefaAtiva = tarefaRepo.findByResponsavelId(id).stream()
                        .anyMatch(t -> !Set.of(StatusTarefa.CANCELADA, StatusTarefa.CONCLUIDA).contains(t.getStatus()));

        if (responsavelTarefaAtiva) {
            throw new ActiveEntityDeletionException("Usuário");
        }

        boolean unicoResponsavelMetaAtiva = metaRepo.findByResponsaveisId(id).stream()
                        .anyMatch(meta -> meta.getResponsaveis().size() == 1 && !Set.of(StatusMeta.ATINGIDA, StatusMeta.PARCIALMENTE_ATINGIDA, StatusMeta.NAO_ATINGIDA, StatusMeta.CANCELADA).contains(meta.getStatus()));

        if (unicoResponsavelMetaAtiva) {
            throw new ActiveEntityDeletionException("Usuário");
        }

        usuario.setStatus(StatusGeral.INATIVO);
        repo.save(usuario);
    }

    @Override
    protected void antesInserir(Usuario usuario, UsuarioRequestDTO dto) {
        if (repo.existsByEmailLoginIgnoreCase(dto.email())) throw new UniqueViolationException("E-mail");

        usuario.setStatus(StatusGeral.ATIVO);
        usuario.setEmpresa(empresaService.getEntity(dto.idEmpresa()));
    }
}
