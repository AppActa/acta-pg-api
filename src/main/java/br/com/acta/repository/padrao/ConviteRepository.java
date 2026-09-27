package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Convite;
import br.com.acta.repository.base.BaseRepository;

import java.util.Optional;

public interface ConviteRepository extends BaseRepository<Convite> {
    Optional<Convite> findByTokenHash(String tokenHash);
}