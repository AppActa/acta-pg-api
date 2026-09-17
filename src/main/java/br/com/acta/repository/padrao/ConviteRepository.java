package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Convite;
import br.com.acta.entity.enums.StatusConvite;
import br.com.acta.repository.base.BaseRepository;

import java.util.Optional;

public interface ConviteRepository extends BaseRepository<Convite> {
    Optional<Convite> findByTokenHash(String tokenHash);
    boolean existsByUsuarioDestinoIdAndStatus(Long idUsuario, StatusConvite status);
    Optional<Convite> findByUsuarioDestinoIdAndStatus(Long usuarioDestinoId, StatusConvite status);
}