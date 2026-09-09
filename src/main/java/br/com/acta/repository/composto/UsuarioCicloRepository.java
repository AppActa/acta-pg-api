package br.com.acta.repository.composto;

import br.com.acta.entity.enums.PapelCiclo;
import br.com.acta.entity.join.UsuarioCiclo;
import br.com.acta.entity.join.id.UsuarioCicloId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioCicloRepository
extends JpaRepository<UsuarioCiclo, UsuarioCicloId> {
    Optional<UsuarioCiclo> findByUsuarioIdAndCicloIdAndCicloEmpresaId(Long idUsuario, Long idCiclo, Long idEmpresa);
    boolean existsByUsuarioIdAndCicloId(Long idUsuario, Long idCiclo);
    List<UsuarioCiclo> findByCicloIdAndPapelCiclo(Long idCiclo, PapelCiclo papelCiclo);
}
