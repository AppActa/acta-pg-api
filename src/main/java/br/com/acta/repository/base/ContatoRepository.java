package br.com.acta.repository.base;

import br.com.acta.entity.base.ContatoBase;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ContatoRepository<ENT extends ContatoBase> extends BaseRepository<ENT>{
    boolean existsByContatoIgnoreCase(String contato);
}
