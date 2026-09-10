package br.com.acta.repository.composto;

import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.join.PriorizacaoProblema;
import br.com.acta.entity.join.id.PriorizacaoProblemaId;
import br.com.acta.entity.pdca.Problema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriorizacaoProblemaRepository
extends JpaRepository<PriorizacaoProblema, PriorizacaoProblemaId> {
    Optional<PriorizacaoProblema> findByProblemaIdAndUsuarioIdAndProblemaCicloEmpresaId(Long idProblema, Long idUsuario, Long idEmpresa);
    List<PriorizacaoProblema> findByProblema(Problema problema);
    List<PriorizacaoProblema> findByProblemaAndUsuario(Problema problema, Usuario usuario);
}
