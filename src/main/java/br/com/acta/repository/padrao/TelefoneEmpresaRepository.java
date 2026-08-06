package br.com.acta.repository.padrao;

import br.com.acta.entity.core.contato.TelefoneEmpresa;
import br.com.acta.repository.base.ContatoRepository;

import java.util.List;

public interface TelefoneEmpresaRepository extends ContatoRepository<TelefoneEmpresa> {
    List<TelefoneEmpresa> findByEmpresa_Id(Long id);

    TelefoneEmpresa findByEmpresaIdAndId(Long id, Long idTelefone);
}
