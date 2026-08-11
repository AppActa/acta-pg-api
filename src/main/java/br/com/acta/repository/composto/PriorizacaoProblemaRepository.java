package br.com.acta.repository.composto;

import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.join.PriorizacaoProblema;
import br.com.acta.entity.join.id.PriorizacaoProblemaId;
import br.com.acta.entity.pdca.Problema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriorizacaoProblemaRepository
extends JpaRepository<PriorizacaoProblema, PriorizacaoProblemaId> {
    boolean existsByUsuarioIdAndProblemaId(Long idUsuario, Long idProblema);
    List<PriorizacaoProblema> findByProblema(Problema problema);
    List<PriorizacaoProblema> findByProblemaAndUsuario(Problema problema, Usuario usuario);
}
