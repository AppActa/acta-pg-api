package br.com.acta.repository.padrao;

import br.com.acta.entity.enums.StatusTarefa;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.repository.base.BaseRepository;

import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends BaseRepository<Tarefa> {
    List<Tarefa> findByPlanoAcaoId(Long idPlanoAcao);
    List<Tarefa> findByResponsavelId(Long idResponsavel);
    List<Tarefa> findByDataFimPrevistaBeforeAndStatusNot(LocalDate hoje, StatusTarefa status);
}
