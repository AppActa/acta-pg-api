package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.VerificacaoResultado;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface VerificacaoResultadoRepository extends BaseRepository<VerificacaoResultado> {
    List<VerificacaoResultado> findByCiclo(Ciclo ciclo);

    Optional<VerificacaoResultado> findByIdAndCicloEmpresaId(Long id, Long cicloEmpresaId);
}
