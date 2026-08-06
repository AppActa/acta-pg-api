package br.com.acta.repository.padrao;

import br.com.acta.entity.core.contato.EmailColaborador;
import br.com.acta.repository.base.ContatoRepository;

import java.util.List;

public interface EmailColaboradorRepository extends ContatoRepository<EmailColaborador> {
    List<EmailColaborador> findByColaborador_Id(Long colaboradorId);
    EmailColaborador findByColaboradorIdAndId(Long colaboradorId, Long id);
}
