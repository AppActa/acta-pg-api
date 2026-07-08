package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.Problema;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface ProblemaRepository extends BaseRepository<Problema> {
    List<Problema> findByCicloId(Long idCiclo);
    List<Problema> findByCicloIdAndProblemaPaiIsNull(Long idCiclo);
}
