package br.com.acta.repository.padrao;

import br.com.acta.entity.core.contato.TelefoneEmpresa;
import br.com.acta.repository.base.ContatoRepository;

import java.util.List;
import java.util.Optional;

public interface TelefoneEmpresaRepository extends ContatoRepository<TelefoneEmpresa> {
    List<TelefoneEmpresa> findByEmpresa_Id(Long id);
    Optional<TelefoneEmpresa> findByEmpresaIdAndId(Long id, Long idTelefone);
    boolean existsByEmpresaIdAndContato(Long empresaId, String contato);
}
