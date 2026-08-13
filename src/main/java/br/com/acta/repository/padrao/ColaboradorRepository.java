package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface ColaboradorRepository extends BaseRepository<Colaborador> {
    List<Colaborador> findAllByStatus(StatusGeral status);
    boolean existsByCpf(String cpf);
}
