package br.com.acta.service;

import br.com.acta.dto.core.empresa.EmpresaRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaResponseDTO;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TamanhoEmpresa;
import br.com.acta.mapper.core.EmpresaMapper;
import br.com.acta.repository.padrao.EmpresaRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.utils.PatchConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Set;

@Service
public class EmpresaService
extends BaseService<EmpresaRequestDTO, EmpresaResponseDTO, Empresa> {
    private final EmpresaRepository repo;
    private final EmpresaMapper mapper;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("cnpj", "nome", "tamanho", "setor", "status"),
            Set.of("nome", "tamanho", "setor", "status")
    );

    public EmpresaService(EmpresaRepository repo, EmpresaMapper mapper) {
        super(repo, mapper, Empresa.class);
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public EmpresaResponseDTO patch(Long id, Map<String, Object> campos) {
        validarCampos(campos, patchConfig);
        Empresa empresa = getEntity(id);

        if (campos.containsKey("nome")) empresa.setNome((String) campos.get("nome"));
        if (campos.containsKey("tamanho")) empresa.setTamanho((TamanhoEmpresa) campos.get("tamanho"));
        if (campos.containsKey("setor")) empresa.setSetor((String) campos.get("setor"));
        if (campos.containsKey("status")) empresa.setStatus((StatusGeral) campos.get("status"));

        Empresa salvo = repo.save(empresa);
        return mapper.toResponse(salvo);
    }

    @Override
    public void excluir(Long id) {
        Empresa empresa = getEntity(id);
        empresa.setStatus(StatusGeral.INATIVO);

        repo.save(empresa);
    }
}
