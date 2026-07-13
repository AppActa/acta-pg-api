package br.com.acta.repository.padrao;

import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface CicloRepository extends BaseRepository<Ciclo> {
    List<Ciclo> findByEmpresaId(Long idEmpresa);
    List<Ciclo> findByGestorId(Long idGestor);
    List<Ciclo> findByEmpresaIdAndStatus(Long idEmpresa, StatusCiclo status);
    List<Ciclo> findByGestorIdAndStatus(Long idGestor, StatusCiclo status);
    List<Ciclo> findByEmpresaIdAndGestorIdAndStatus(Long idEmpresa, Long idGestor, StatusCiclo status);
}
