package br.com.acta.service;

import br.com.acta.common.handler.exception.ActiveEntityDeletionException;
import br.com.acta.common.handler.exception.InvalidRequestException;
import br.com.acta.common.handler.exception.StatusUpdateException;
import br.com.acta.common.utils.ConversorObject;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.mapper.pdca.CicloMapper;
import br.com.acta.dto.pdca.ciclo.CicloRequestDTO;
import br.com.acta.dto.pdca.ciclo.CicloResponseDTO;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.repository.padrao.CicloRepository;
import br.com.acta.service.base.BaseService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class CicloService
extends BaseService <CicloRequestDTO, CicloResponseDTO, Ciclo>{
    private final CicloRepository repo;
    private final CicloMapper mapper;
    private final PatchConfig patchConfig = new PatchConfig(
            Set.of("titulo", "descricao", "dataInicio", "dataEstimadaFim", "idGestor", "idEmpresa"),
            Set.of("titulo", "descricao", "dataEstimadaFim")
    );
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;

    public CicloService(CicloRepository repo, CicloMapper mapper, UsuarioService usuarioService, EmpresaService empresaService, AuthService authService) {
        super(repo, mapper, Ciclo.class, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public CicloResponseDTO patch(Long id, Map<String, Object> campos) {
        Validador.validarCampos(campos, patchConfig);
        Ciclo ciclo = getEntity(id);
        Validador.validarCicloAberto(ciclo);

        if (campos.containsKey("titulo")) ciclo.setTitulo((String) campos.get("titulo"));
        if (campos.containsKey("descricao")) ciclo.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("dataEstimadaFim")) {
            Object dataObject = campos.get("dataEstimadaFim");
            ciclo.setDataEstimadaFim(ConversorObject.toLocalDate(dataObject, false));
        }

        Ciclo salvo = repo.save(ciclo);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public CicloResponseDTO patchStatus(Long id, StatusCiclo status){
        Ciclo ciclo = getEntity(id);

        if (status == StatusCiclo.CONCLUIDO) {
            if (!repo.podeEncerrarCiclo(id)) throw new ActiveEntityDeletionException("ciclo");

            repo.encerrarCiclo(id);
            Ciclo cicloConcluido = getEntity(id);
            return mapper.toResponse(cicloConcluido);
        }

        if (!ciclo.getStatus().podeAtualizarStatus(status)) throw new StatusUpdateException(ciclo.getStatus().toString(), status.toString());

        ciclo.setStatus(status);
        Ciclo salvo = repo.save(ciclo);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public void excluir(Long id) {
        Ciclo ciclo = getEntity(id);

        Validador.validarCicloAberto(ciclo);
        ciclo.setStatus(StatusCiclo.CANCELADO);

        repo.save(ciclo);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<CicloResponseDTO> buscarPorStatus(Long idEmpresa, Long idGestor, StatusCiclo status){
        if (idEmpresa == null && idGestor == null) {
            throw new InvalidRequestException("É necessário informar pelo menos o ID da empresa ou do gestor");
        }

        List<Ciclo> ciclos = repo.buscar(idEmpresa, idGestor, status);
        return mapper.toResponseList(ciclos);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public Double avancoCiclo(Long id) {
        return repo.avancoCiclo(id);
    }

    @Override
    protected void antesInserir(Ciclo ciclo, CicloRequestDTO dto) {
        Empresa empresa = empresaService.getEntity(dto.idEmpresa());
        Usuario gestor = usuarioService.getEntity(dto.idGestor());

        ciclo.setStatus(StatusCiclo.PLANEJAMENTO);
        ciclo.setEmpresa(empresa);
        ciclo.setGestor(gestor);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Override
    public CicloResponseDTO inserir(CicloRequestDTO dto) {
        return super.inserir(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public CicloResponseDTO buscar(Long id) {
        return super.buscar(id);
    }
}
