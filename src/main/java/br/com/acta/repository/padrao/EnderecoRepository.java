package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Endereco;
import br.com.acta.repository.base.BaseRepository;
import java.util.Optional;

public interface EnderecoRepository extends BaseRepository<Endereco> {
    Optional<Endereco> findByEmpresaAndId(Empresa empresa, Long idEndereco);
}
