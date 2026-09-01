package br.com.acta.repository.padrao;

import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CicloRepository extends BaseRepository<Ciclo> {
    @Query("""
    SELECT c FROM Ciclo c
    WHERE (:idEmpresa IS NULL OR c.empresa.id = :idEmpresa)
      AND (:idGestor IS NULL OR c.gestor.id = :idGestor)
      AND (:status IS NULL OR c.status = :status)
    """)
    List<Ciclo> buscar(@Param("idEmpresa") Long idEmpresa, @Param("idGestor") Long idGestor, @Param("status") StatusCiclo status);

    @Query("SELECT fn_avanco_ciclo(:cicloId)")
    Double avancoCiclo(@Param("cicloId") Long cicloId);

    @Procedure(procedureName = "pdca.pr_encerrar_ciclo")
    void encerrarCiclo(@Param("p_ciclo_id") Long idCiclo);

    @Query(value = "SELECT pdca.fn_pode_encerrar_ciclo(:cicloId)", nativeQuery = true)
    boolean podeEncerrarCiclo(@Param("cicloId") Long cicloId);
}
