package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.core.contato.EmailColaborador;
import br.com.acta.repository.base.BaseRepository;

import java.util.Optional;
import java.util.Set;

public interface ColaboradorRepository extends BaseRepository<Colaborador> {
    Optional<Colaborador> findByCpf(String cpf);
}
