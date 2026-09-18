package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Convite;
import br.com.acta.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConviteRepository extends BaseRepository<Convite> {

    @Query("""
        SELECT c FROM Convite c
        JOIN FETCH c.usuarioDestino u
        JOIN FETCH u.empresa
        LEFT JOIN FETCH u.colaborador
        WHERE c.tokenHash = :tokenHash
        """)
    Optional<Convite> findByTokenHash(String tokenHash);
}