package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.EfeitoSecundario;
import br.com.acta.entity.pdca.VerificacaoResultado;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface EfeitoSecundarioRepository extends BaseRepository<EfeitoSecundario> {
    List<EfeitoSecundario> findByVerificacaoResultado(VerificacaoResultado verificacaoResultado);
}
