package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.AlertaPrazo;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface AlertaPrazoRepository extends BaseRepository<AlertaPrazo> {
    List<AlertaPrazo> findByUsuarioDestinoIdAndLidoEmIsNull(Long idUsuario);
    Optional<AlertaPrazo> findByTarefa(Tarefa tarefa);
}
