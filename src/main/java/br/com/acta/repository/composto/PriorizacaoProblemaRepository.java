package br.com.acta.repository.composto;

import br.com.acta.entity.join.PriorizacaoProblema;
import br.com.acta.entity.join.id.PriorizacaoProblemaId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriorizacaoProblemaRepository
extends JpaRepository<PriorizacaoProblema, PriorizacaoProblemaId> {
    boolean existsByUsuarioIdAndProblemaId(Long idUsuario, Long idProblema);
    List<PriorizacaoProblema> findByProblemaId(Long idProblema);
    List<PriorizacaoProblema> findByUsuarioId(Long idUsuario);
}
