package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.Meta;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface MetaRepository extends BaseRepository<Meta> {
    List<Meta> findByCicloId(Long idCiclo);
    List<Meta> findByResponsaveisId(Long idResponsavel);
}
