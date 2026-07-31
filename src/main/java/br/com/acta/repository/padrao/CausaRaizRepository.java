package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.CausaRaiz;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface CausaRaizRepository extends BaseRepository<CausaRaiz> {
    List<CausaRaiz> findByProblemaId(Long idProblema);
    List<CausaRaiz> findByCicloIdAndPrincipalTrue(Long idCiclo);
}
