package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface PlanoAcaoRepository extends BaseRepository<PlanoAcao> {
    List<PlanoAcao> findByCicloId(Long idCiclo);
}
