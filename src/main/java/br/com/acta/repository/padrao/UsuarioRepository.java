package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends BaseRepository<Usuario> {
    Optional<Usuario> findByEmailLogin(String emailLogin);
    boolean existsByEmailLogin(String emailLogin);
    List<Usuario> findByTipoAndStatusAndEmpresaId(TipoUsuario tipo, StatusGeral status, Long empresaId);
    List<Usuario> findByEmpresaIdAndStatus(Long empresaId, StatusGeral status);
}
