package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface ColaboradorRepository extends BaseRepository<Colaborador> {
    Optional<Colaborador> findByCpf(String cpf);

    List<Colaborador> findAllByStatus(StatusGeral status);
}
