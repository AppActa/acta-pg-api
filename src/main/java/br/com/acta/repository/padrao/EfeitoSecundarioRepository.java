package br.com.acta.repository.padrao;

import br.com.acta.entity.pdca.EfeitoSecundario;
import br.com.acta.entity.pdca.VerificacaoResultado;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface EfeitoSecundarioRepository extends BaseRepository<EfeitoSecundario> {
    Optional<EfeitoSecundario> findByIdAndVerificacaoResultadoCicloEmpresaId(Long id, Long idEmpresa);
    List<EfeitoSecundario> findByVerificacaoResultado(VerificacaoResultado verificacaoResultado);
}
