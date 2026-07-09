package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.AlertaPrazo;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface AlertaPrazoRepository extends BaseRepository<AlertaPrazo> {
    List<AlertaPrazo> findByUsuarioDestinoIdAndLidoEmIsNull(Long idUsuario);
}
