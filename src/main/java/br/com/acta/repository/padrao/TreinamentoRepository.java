package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.Treinamento;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface TreinamentoRepository extends BaseRepository<Treinamento> {
    Optional<Treinamento> findByIdAndCicloEmpresaId(Long id, Long idEmpresa);
    List<Treinamento> findByCiclo(Ciclo ciclo);
}
