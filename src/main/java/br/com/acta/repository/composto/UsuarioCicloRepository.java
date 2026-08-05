package br.com.acta.repository.composto;

import br.com.acta.entity.enums.PapelCiclo;
import br.com.acta.entity.join.UsuarioCiclo;
import br.com.acta.entity.join.id.UsuarioCicloId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioCicloRepository
extends JpaRepository<UsuarioCiclo, UsuarioCicloId> {
    boolean existsByUsuarioIdAndCicloId(Long idUsuario, Long idCiclo);
    List<UsuarioCiclo> findByCicloIdAndPapelCiclo(Long idCiclo, PapelCiclo papelCiclo);
    List<UsuarioCiclo> findByCicloId(Long idCiclo);
    List<UsuarioCiclo> findByUsuarioId(Long idUsuario);
}
