package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Convite;
import br.com.acta.repository.base.BaseRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public interface ConviteRepository extends BaseRepository<Convite> {
    Optional<Convite> findByTokenHash(String tokenHash);
    List<Convite> findAllByUsuarioIdAndStatusAndExpiraEmAfter(Long idUsuario, String status, OffsetDateTime agora);
    boolean existsByUsuarioIdAndStatusAndExpiraEmAfter(Long idUsuario, String status, OffsetDateTime agora);
}