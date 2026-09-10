package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.AlertaPrazo;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import java.util.Optional;

public interface AlertaPrazoRepository extends BaseRepository<AlertaPrazo> {
    Optional<AlertaPrazo> findByTarefa(Tarefa tarefa);
    Optional<AlertaPrazo> findByIdAndTarefaPlanoAcaoCicloEmpresaId(Long id, Long idEmpresa);

    @Procedure(procedureName = "pdca.pr_gerar_alertas_atraso")
    void gerarAlertasAtraso();
}
