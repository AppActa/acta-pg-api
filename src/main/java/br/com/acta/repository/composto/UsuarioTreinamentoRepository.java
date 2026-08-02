package br.com.acta.repository.composto;

import br.com.acta.entity.join.UsuarioTreinamento;
import br.com.acta.entity.join.id.UsuarioTreinamentoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioTreinamentoRepository
extends JpaRepository<UsuarioTreinamento, UsuarioTreinamentoId> {
    boolean existsByUsuarioIdAndTreinamentoId(Long idUsuario, Long idTreinamento);
    List<UsuarioTreinamento> findByTreinamentoId(Long idTreinamento);
    List<UsuarioTreinamento> findByUsuarioId(Long idUsuario);
    UsuarioTreinamento findByUsuarioIdAndTreinamentoId(Long usuarioId, Long treinamentoId);
}
