package br.com.acta.repository.padrao;

import br.com.acta.entity.core.contato.TelefoneColaborador;
import br.com.acta.repository.base.ContatoRepository;

import java.util.List;

public interface TelefoneColaboradorRepository extends ContatoRepository<TelefoneColaborador> {
    List<TelefoneColaborador> findByColaborador_Id(Long id);
    TelefoneColaborador findByColaboradorIdAndId(Long colaboradorId, Long id);
}
