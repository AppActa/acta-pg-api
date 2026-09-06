package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface ColaboradorRepository extends BaseRepository<Colaborador> {
    List<Colaborador> findAllByStatus(StatusGeral status);
    boolean existsByCpf(String cpf);

    boolean existsByIdAndEmpresaId(Long id, Long empresaId);

    Optional<Colaborador> findByIdAndEmpresaId(Long id, Long empresaId);
}
