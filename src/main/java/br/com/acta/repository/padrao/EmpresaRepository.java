package br.com.acta.repository.padrao;

import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TamanhoEmpresa;
import br.com.acta.repository.base.BaseRepository;

import java.util.List;

public interface EmpresaRepository extends BaseRepository<Empresa> {
    boolean existsByCnpj(String cnpj);
    List<Empresa> findAllByStatus(StatusGeral status);
    List<Empresa> findByTamanhoAndStatus(TamanhoEmpresa tamanho, StatusGeral status);
}
