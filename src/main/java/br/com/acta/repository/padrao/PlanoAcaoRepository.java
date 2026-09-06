package br.com.acta.repository.padrao;

import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface PlanoAcaoRepository extends BaseRepository<PlanoAcao> {
    List<PlanoAcao> findByCicloId(Long idCiclo);
    List<PlanoAcao> findByCicloIdAndStatus(Long cicloId, StatusPlanoAcao status);
    List<PlanoAcao> findByCicloIdAndPrioridade(Long cicloId, Prioridade prioridade);
    List<PlanoAcao> findByCicloIdAndStatusAndPrioridade(Long cicloId, StatusPlanoAcao status, Prioridade prioridade);

    Optional<PlanoAcao> findByIdAndCicloEmpresaId(Long id, Long cicloEmpresaId);
}
