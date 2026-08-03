package br.com.acta.repository.padrao;

import br.com.acta.entity.enums.StatusProblema;
import br.com.acta.entity.pdca.Problema;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface ProblemaRepository extends BaseRepository<Problema> {
    List<Problema> findByCicloId(Long idCiclo);
    List<Problema> findByCicloIdAndProblemaPaiId(Long idCiclo, Long idProblemaPai);
    List<Problema> findByStatusAndCicloId(StatusProblema status, Long idCiclo);
    List<Problema> findByStatusAndProblemaPaiIdAndCicloId(StatusProblema status, Long idProblemaPai, Long idCiclo);
}
