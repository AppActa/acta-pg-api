package br.com.acta.repository.padrao;

import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusTarefa;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends BaseRepository<Tarefa> {
    List<Tarefa> findByResponsavelId(Long idResponsavel);
    List<Tarefa> findByDataFimPrevistaBeforeAndStatusNot(LocalDate hoje, StatusTarefa status);

    @Query("""
    SELECT t FROM Tarefa t WHERE t.planoAcao.id = :idPlanoAcao
        AND (:status IS NULL OR t.status = :status)
        AND (:idResponsavel IS NULL OR t.responsavel.id = :idResponsavel)
        AND (:prioridade IS NULL OR t.prioridade = :prioridade)
    """)
    List<Tarefa> buscar(
            @Param("idPlanoAcao") Long idPlanoAcao,
            @Param("status") StatusTarefa statusTarefa,
            @Param("idResponsavel") Long idResponsavel,
            @Param("prioridade") Prioridade prioridade);

    @Query(value = "SELECT pdca.fn_pode_iniciar_tarefa(:idTarefa, :idUsuario)", nativeQuery = true)
    Boolean podeIniciarTarefa(@Param("idTarefa") Long idTarefa, @Param("idUsuario") Long idUsuario);

    @Procedure(procedureName = "pdca.pr_reabrir_tarefa")
    void reabrirTarefa(@Param("p_tarefa_id") Long tarefaId, @Param("p_novo_prazo") LocalDate novoPrazo);
}
