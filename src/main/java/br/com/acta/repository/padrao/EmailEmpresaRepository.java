package br.com.acta.repository.padrao;

import br.com.acta.entity.core.contato.EmailEmpresa;
import br.com.acta.repository.base.ContatoRepository;

import java.util.List;
import java.util.Optional;

public interface EmailEmpresaRepository extends ContatoRepository<EmailEmpresa> {
    Optional<EmailEmpresa> findByEmpresaIdAndId(Long idEmpresa, Long idEmail);
    List<EmailEmpresa> findByEmpresa_Id(Long id);
}
