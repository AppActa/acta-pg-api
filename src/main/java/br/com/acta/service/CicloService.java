package br.com.acta.service;

import br.com.acta.common.handler.exception.ActiveEntityDeletionException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.handler.exception.StatusUpdateException;
import br.com.acta.common.client.FcmApiClient;
import br.com.acta.common.utils.ConversorObject;
import br.com.acta.common.utils.PatchConfig;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.evento.TipoEventoNotificacao;
import br.com.acta.dto.pdca.ciclo.CicloMapper;
import br.com.acta.dto.pdca.ciclo.CicloRequestDTO;
import br.com.acta.dto.pdca.ciclo.CicloResponseDTO;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.IconeCiclo;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.join.UsuarioCiclo;
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
            Set.of("titulo", "descricao", "dataInicio", "dataEstimadaFim", "iconeUrl", "idGestor", "idEmpresa"),
            Set.of("titulo", "descricao", "dataEstimadaFim", "iconeUrl")
    );
    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;
    private final FcmApiClient fcmApiClient;

    public CicloService(CicloRepository repo, CicloMapper mapper, UsuarioService usuarioService, EmpresaService empresaService, AuthService authService, FcmApiClient fcmApiClient) {
        super(repo, mapper, authService);
        this.repo = repo;
        this.mapper = mapper;
        this.empresaService = empresaService;
        this.usuarioService = usuarioService;
        this.fcmApiClient = fcmApiClient;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public CicloResponseDTO patch(Long id, Map<String, Object> campos) {
        configurarUsuarioAtual();
        Validador.validarCampos(campos, patchConfig);
        Ciclo ciclo = getEntity(id);
        Validador.validarCicloAberto(ciclo);

        if (campos.containsKey("titulo")) ciclo.setTitulo((String) campos.get("titulo"));
        if (campos.containsKey("descricao")) ciclo.setDescricao((String) campos.get("descricao"));
        if (campos.containsKey("dataEstimadaFim")) {
            Object dataObject = campos.get("dataEstimadaFim");
            ciclo.setDataEstimadaFim(ConversorObject.toLocalDate(dataObject, false));
        }
        if (campos.containsKey("iconeUrl")) {
            ciclo.setIconeUrl(ConversorObject.toIconeCicloUrl(campos.get("iconeUrl")));
        }

        Ciclo salvo = repo.save(ciclo);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public CicloResponseDTO patchStatus(Long id, StatusCiclo status){
        configurarUsuarioAtual();
        Ciclo ciclo = getEntity(id);
        StatusCiclo statusAnterior = ciclo.getStatus();

        if (status == StatusCiclo.CONCLUIDO) {
            if (!repo.podeEncerrarCiclo(id)) throw new ActiveEntityDeletionException("ciclo");

            repo.encerrarCiclo(id);
            Ciclo cicloConcluido = getEntity(id);
            if (statusAnterior != StatusCiclo.CONCLUIDO && cicloConcluido.getStatus() == StatusCiclo.CONCLUIDO)
                notificarParticipantes(cicloConcluido, TipoEventoNotificacao.CICLO_CONCLUIDO);
            return mapper.toResponse(cicloConcluido);
        }

        if (!ciclo.getStatus().podeAtualizarStatus(status)) throw new StatusUpdateException(ciclo.getStatus().toString(), status.toString());

        ciclo.setStatus(status);
        Ciclo salvo = repo.save(ciclo);
        if (statusAnterior != status && (status == StatusCiclo.PAUSADO || status == StatusCiclo.CANCELADO))
            notificarParticipantes(salvo, status == StatusCiclo.PAUSADO
                    ? TipoEventoNotificacao.CICLO_PAUSADO : TipoEventoNotificacao.CICLO_CANCELADO);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    @Override
    public void excluir(Long id) {
        configurarUsuarioAtual();
        Ciclo ciclo = getEntity(id);

        Validador.validarCicloAberto(ciclo);
        ciclo.setStatus(StatusCiclo.CANCELADO);

        repo.save(ciclo);
        notificarParticipantes(ciclo, TipoEventoNotificacao.CICLO_CANCELADO);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<CicloResponseDTO> buscarPorStatus(Long idGestor, StatusCiclo status){
        List<Ciclo> ciclos = repo.buscar(atual().idEmpresa(), idGestor, status);
        return mapper.toResponseList(ciclos);
    }

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public Double avancoCiclo(Long id) {
        getEntity(id);
        return repo.avancoCiclo(id);
    }

    @Override
    protected void antesInserir(Ciclo ciclo, CicloRequestDTO dto) {
        Empresa empresa = empresaService.getEntity(atual().idEmpresa());
        Usuario gestor = usuarioService.getEntity(dto.idGestor());

        if (ciclo.getIconeUrl() == null) ciclo.setIconeUrl(IconeCiclo.PEOPLE.getUrl());
        else ciclo.setIconeUrl(ConversorObject.toIconeCicloUrl(ciclo.getIconeUrl()));

        ciclo.setStatus(StatusCiclo.PLANEJAMENTO);
        ciclo.setEmpresa(empresa);
        ciclo.setGestor(gestor);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Override
    @Transactional
    public CicloResponseDTO inserir(CicloRequestDTO dto) {
        return super.inserir(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @Override
    public CicloResponseDTO buscar(Long id) {
        return super.buscar(id);
    }

    @Override
    public Ciclo getEntity(Long id) {
        return repo.findByIdAndEmpresaId(id, atual().idEmpresa())
                .orElseThrow(() -> new ModelNotFoundException("Ciclo", id));
    }

    private void notificarParticipantes(Ciclo ciclo, TipoEventoNotificacao tipo) {
        Long idAtor = atual().idUsuario();
        List<Long> destinatarios = ciclo.getColaboradores().stream()
                .map(UsuarioCiclo::getUsuario)
                .filter(usuario -> usuario.getStatus() == StatusGeral.ATIVO && !usuario.getId().equals(idAtor))
                .map(Usuario::getId)
                .distinct()
                .toList();
        if (destinatarios.isEmpty()) return;
        Long cicloId = ciclo.getId();
        Long empresaId = ciclo.getEmpresa().getId();

        fcmApiClient.publicarStatusCiclo(tipo, cicloId, empresaId, destinatarios);
    }
}
