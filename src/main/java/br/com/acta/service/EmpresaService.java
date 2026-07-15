package br.com.acta.service;

import br.com.acta.dto.core.empresa.EmpresaRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaResponseDTO;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TamanhoEmpresa;
import br.com.acta.handler.exception.ActiveEntityDeletionException;
import br.com.acta.handler.exception.ModelNotFoundException;
import br.com.acta.handler.exception.RegexException;
import br.com.acta.handler.exception.UniqueViolationException;
import br.com.acta.mapper.core.EmpresaMapper;
import br.com.acta.repository.padrao.EmpresaRepository;
import br.com.acta.service.base.BaseService;
import br.com.acta.utils.PatchConfig;
import br.com.acta.utils.Validador;
import br.com.caelum.stella.validation.CNPJValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    public EmpresaService(EmpresaRepository repo, EmpresaMapper mapper) {
        super(repo, mapper, Empresa.class);
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public EmpresaResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Empresa empresa = getEntity(id);

        if (campos.containsKey("nome")) empresa.setNome((String) campos.get("nome"));
        if (campos.containsKey("tamanho")) empresa.setTamanho((TamanhoEmpresa) campos.get("tamanho"));
        if (campos.containsKey("setor")) empresa.setSetor((String) campos.get("setor"));
        if (campos.containsKey("status")) empresa.setStatus((StatusGeral) campos.get("status"));

        Empresa salvo = repo.save(empresa);
        return mapper.toResponse(salvo);
    }

    @Override
    public List<EmpresaResponseDTO> buscar() {
        List<Empresa> empresas = repo.findAllByStatus(StatusGeral.ATIVO);
        verificarListaVazia(empresas);

        return mapper.toResponseList(empresas);
    }

    public List<EmpresaResponseDTO> buscar(TamanhoEmpresa tamanho){
        List<Empresa> empresas = repo.findByTamanhoAndStatus(tamanho, StatusGeral.ATIVO);
        verificarListaVazia(empresas);

        return mapper.toResponseList(empresas);
    }

    @Override
    public EmpresaResponseDTO buscar(Long id) {
        Empresa empresa = getEntity(id);

        if (empresa.getStatus().equals(StatusGeral.INATIVO)) throw new ModelNotFoundException("Empresa", id);
        return mapper.toResponse(empresa);
    }

    @Override
    public void excluir(Long id) {
        Empresa empresa = getEntity(id);

        empresa.getCiclos().forEach(ciclo -> {
            if (!ciclo.getStatus().equals(StatusCiclo.CANCELADO) && !ciclo.getStatus().equals(StatusCiclo.CONCLUIDO)){
                throw new ActiveEntityDeletionException("Empresa");
            }
        });

        empresa.setStatus(StatusGeral.INATIVO);

        repo.save(empresa);
    }

    @Override
    protected void antesInserir(Empresa empresa, EmpresaRequestDTO dto) {
        empresa.setStatus(StatusGeral.ATIVO);

        // validando cnpj
        if (!cnpjValidator.isEligible(dto.cnpj())) throw new RegexException("CNPJ");
        if (repo.existsByCnpj(dto.cnpj())) throw new UniqueViolationException("CNPJ");
    }
}
