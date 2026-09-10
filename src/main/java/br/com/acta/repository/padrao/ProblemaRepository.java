package br.com.acta.repository.padrao;

import br.com.acta.entity.enums.StatusProblema;
import br.com.acta.entity.pdca.Problema;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface ProblemaRepository extends BaseRepository<Problema> {
    Optional<Problema> findByIdAndCicloEmpresaId(Long id, Long idEmpresa);
    List<Problema> findByCicloId(Long idCiclo);
    List<Problema> findByCicloIdAndProblemaPaiId(Long idCiclo, Long idProblemaPai);
    List<Problema> findByStatusAndCicloId(StatusProblema status, Long idCiclo);
    List<Problema> findByStatusAndProblemaPaiIdAndCicloId(StatusProblema status, Long idProblemaPai, Long idCiclo);
}
