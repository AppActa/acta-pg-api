package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.Plano5W2H;
import br.com.acta.repository.base.BaseRepository;

import java.util.Optional;

public interface Plano5W2HRepository extends BaseRepository<Plano5W2H> {
    Optional<Plano5W2H> findByPlanoAcaoId(Long idPlanoAcao);
}
