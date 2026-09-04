package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends BaseRepository<Usuario> {
    boolean existsByEmailLoginIgnoreCase(String emailLogin);
    List<Usuario> findByTipoAndStatusAndEmpresaId(TipoUsuario tipo, StatusGeral status, Long empresaId);
    List<Usuario> findByEmpresaIdAndStatus(Long empresaId, StatusGeral status);

    boolean existsByFirebaseUid(String firebaseUid);

    // busca usuário pelo firebaseUid e já carrega empresa e colaborador
    @Query("""
        SELECT u FROM Usuario u
        JOIN FETCH u.empresa
        LEFT JOIN FETCH u.colaborador
        WHERE u.firebaseUid = :firebaseUid""")
    Optional<Usuario> findByFirebaseUid(String firebaseUid);

    @Query("""
        SELECT u FROM Usuario u
        JOIN FETCH u.empresa
        LEFT JOIN FETCH u.colaborador
        WHERE LOWER(u.emailLogin) = LOWER(:emailLogin)""")
    Optional<Usuario> findByEmailLoginIgnoreCase(String emailLogin);

    boolean existsByIdAndEmpresaId(Long id, Long empresaId);
}
