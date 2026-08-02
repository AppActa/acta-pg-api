package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.Treinamento;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface TreinamentoRepository extends BaseRepository<Treinamento> {
    List<Treinamento> findByCiclo(Ciclo ciclo);
}
