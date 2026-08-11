package br.com.acta.repository.padrao;

import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusMeta;
import br.com.acta.entity.pdca.Meta;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface MetaRepository extends BaseRepository<Meta> {
    List<Meta> findByCicloId(Long idCiclo);
    List<Meta> findByResponsaveisId(Long idResponsavel);
    List<Meta> findByCicloIdAndStatus(Long cicloId, StatusMeta status);
    List<Meta> findByCicloIdAndPrioridade(Long cicloId, Prioridade prioridade);
    List<Meta> findByCicloIdAndStatusAndPrioridade(Long cicloId, StatusMeta status, Prioridade prioridade);
}
