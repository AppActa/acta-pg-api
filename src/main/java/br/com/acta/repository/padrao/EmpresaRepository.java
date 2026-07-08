package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Empresa;
import br.com.acta.repository.base.BaseRepository;

public interface EmpresaRepository extends BaseRepository<Empresa> {
    boolean existsByCnpj(String cnpj);
}
