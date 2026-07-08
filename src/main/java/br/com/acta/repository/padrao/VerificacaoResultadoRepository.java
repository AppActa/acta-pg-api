package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.VerificacaoResultado;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface VerificacaoResultadoRepository extends BaseRepository<VerificacaoResultado> {
    List<VerificacaoResultado> findByCicloId(Long idCiclo);
}
