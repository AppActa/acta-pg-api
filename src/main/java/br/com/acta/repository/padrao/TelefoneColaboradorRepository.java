package br.com.acta.repository.padrao;

import br.com.acta.entity.core.contato.TelefoneColaborador;
import br.com.acta.repository.base.ContatoRepository;

import java.util.List;
import java.util.Optional;

public interface TelefoneColaboradorRepository extends ContatoRepository<TelefoneColaborador> {
    List<TelefoneColaborador> findByColaborador_Id(Long id);
    Optional<TelefoneColaborador> findByColaboradorIdAndId(Long colaboradorId, Long id);
}
