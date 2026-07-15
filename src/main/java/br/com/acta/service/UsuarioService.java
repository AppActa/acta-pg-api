package br.com.acta.service;

import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.mapper.core.UsuarioMapper;
import br.com.acta.repository.padrao.UsuarioRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.utils.Hash;
import br.com.acta.utils.PatchConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UsuarioService
extends BaseService<UsuarioRequestDTO, UsuarioResponseDTO, Usuario> {
    private final EmpresaService empresaService;
    private final UsuarioRepository repo;
    protected final UsuarioMapper mapper;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("nome", "email", "senha", "tipo", "idEmpresa"),
            Set.of("nome", "email", "senha", "tipo")
    );

    public UsuarioService(EmpresaService empresaService, UsuarioRepository repo, UsuarioMapper mapper){
        super(repo, mapper, Usuario.class);
        this.empresaService = empresaService;
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public UsuarioResponseDTO patch(Long id, Map<String, Object> campos) {
        validarCampos(campos, patchConfig);
        Usuario usuario = getEntity(id);

        // todo verificar se e-mail já  (nas três tabelas)
        if (campos.containsKey("nome")) usuario.setNome((String) campos.get("nome"));
        if (campos.containsKey("email")) usuario.setEmailLogin((String) campos.get("email"));
        if (campos.containsKey("senha")) usuario.setSenhaHash((String) campos.get("senha"));
        if (campos.containsKey("tipo")) usuario.setTipo((TipoUsuario) campos.get("tipo"));

        repo.save(usuario);
        return mapper.toResponse(usuario);
    }

    public List<UsuarioResponseDTO> buscarTodos(Long idEmpresa) {
        List<Usuario> usuarios = repo.findByEmpresaIdAndStatus(idEmpresa, StatusGeral.ATIVO);
        verificarListaVazia(usuarios);

        return mapper.toResponseList(usuarios);
    }

    public List<UsuarioResponseDTO> buscarTodos(Long idEmpresa, TipoUsuario tipo){
        List<Usuario> usuarios = repo.findByTipoAndStatusAndEmpresaId(tipo, StatusGeral.ATIVO, idEmpresa);
        verificarListaVazia(usuarios);

        return mapper.toResponseList(usuarios);
    }

    @Override
    public void excluir(Long id) {
        Usuario usuario = getEntity(id);
        usuario.setStatus(StatusGeral.INATIVO);

        repo.save(usuario);
    }

    @Override
    protected void antesInserir(Usuario usuario, UsuarioRequestDTO dto) {
        usuario.setStatus(StatusGeral.ATIVO);
        usuario.setEmpresa(empresaService.getEntity(dto.idEmpresa()));
        usuario.setSenhaHash(Hash.gerarHash(dto.senha()));
    }
}
