package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.CausaRaiz;
import br.com.acta.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CausaRaizRepository extends BaseRepository<CausaRaiz> {
    List<CausaRaiz> findByProblemaId(Long idProblema);
    List<CausaRaiz> findByCicloIdAndPrincipalTrue(Long idCiclo);

    @Query("""
        SELECT c FROM CausaRaiz c
        WHERE (:idCiclo IS NULL OR c.ciclo.id = :idCiclo)
        AND (:idProblema IS NULL OR c.problema.id = :idProblema)
        AND (:aceita IS NULL OR c.aceita = :aceita)
        AND (:principal IS NULL OR c.principal = :principal)
    """)
    List<CausaRaiz> buscar(
            @Param("idCiclo") Long idCiclo,
            @Param("idProblema") Long idProblema,
            @Param("aceita") Boolean aceita,
            @Param("principal") Boolean principal
    );
}
