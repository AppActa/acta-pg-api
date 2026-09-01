package br.com.acta.service;

import br.com.acta.common.config.security.FirebaseAuthFilter.FirebaseIdentity;
import br.com.acta.common.config.security.FirebaseUtils;
import br.com.acta.common.config.security.UsuarioAutenticado;
import br.com.acta.common.handler.exception.FirebaseAccessRevokedException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.dto.auth.MeResponseDTO;
import br.com.acta.dto.mapper.auth.AuthMapper;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.repository.padrao.CicloRepository;
import br.com.acta.repository.padrao.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository repo;
    private final CicloRepository cicloRepo;
    private final AuthMapper mapper;
    private final FirebaseUtils utils;

    @Transactional(readOnly = true)
    public UsuarioAutenticado atual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UsuarioAutenticado usuario) || !auth.isAuthenticated())
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado");

        return usuario;
    }

    @PreAuthorize("hasRole('ROLE_FIREBASE')")
    @Transactional
    public MeResponseDTO ativar(FirebaseIdentity identity) {
        if (!identity.emailVerificado()) throw new FirebaseAccessRevokedException();

        Usuario usuario = repo.findByEmailLoginIgnoreCase(identity.email())
                .orElseThrow(() -> new ModelNotFoundException("Usuário"));
        utils.validarAcesso(usuario);

        if (usuario.getFirebaseUid() != null && !usuario.getFirebaseUid().equals(identity.firebaseUid())) throw new FirebaseAccessRevokedException();
        String firebaseUid = usuario.getFirebaseUid();

        if (firebaseUid == null) {
            if (repo.existsByFirebaseUid(identity.firebaseUid())) throw new FirebaseAccessRevokedException();
            usuario.setFirebaseUid(identity.firebaseUid());
            repo.save(usuario);
        }

        UsuarioAutenticado usuarioAuth = mapper.toUsuarioAutenticado(usuario);
        return mapper.toMeResponse(usuarioAuth);
    }

    public boolean admin() {
        return atual().tipo() == TipoUsuario.ADMIN;
    }

    public boolean podeGerenciar() {
        UsuarioAutenticado usuario = atual();
        return List.of(TipoUsuario.ADMIN, TipoUsuario.GESTOR).contains(usuario.tipo()) || usuario.permissaoGestor();
    }

    public void exigirGestor() {
        if (!podeGerenciar()) throw new AccessDeniedException("Usuário não tem permissão de gestor");
    }

    public boolean ehProprioUsuario(Long idUsuario){
        return Objects.equals(atual().idUsuario(), idUsuario);
    }

    public boolean ehUsuarioEmpresa(Long idEmpresa) {
        return Objects.equals(atual().idEmpresa(), idEmpresa);
    }

    public boolean ehAdmin(Long idUsuario) {
        Usuario usuario = repo.findById(idUsuario)
                .orElseThrow(() -> new ModelNotFoundException("Usuário"));
        return usuario.getTipo() == TipoUsuario.ADMIN;
    }

    public boolean ehGestor(Long idGestor) {
        return atual().tipo() == TipoUsuario.ADMIN;
    }

    public boolean ehAdminEmpresaUsuario(Long idUsuario){
        UsuarioAutenticado usuarioAuth = atual();

        if (usuarioAuth.tipo() != TipoUsuario.ADMIN) return false;

        Usuario usuarioAlvo = repo.findById(idUsuario).orElseThrow(() -> new ModelNotFoundException("Usuário"));
        return Objects.equals(usuarioAuth.idEmpresa(), usuarioAlvo.getEmpresa().getId());
    }

    public boolean podeBuscarCiclos(Long idEmpresa, Long idGestor) {
        UsuarioAutenticado usuarioAuth = atual();
        if (idEmpresa != null && !Objects.equals(usuarioAuth.idEmpresa(), idEmpresa)) return false;

        if (idGestor != null) {
            Usuario gestor = repo.findById(idGestor).orElseThrow(() -> new ModelNotFoundException("Gestor"));
            if (!Objects.equals(usuarioAuth.idEmpresa(), gestor.getEmpresa().getId())) return false;
        }

        return true;
    }

    public boolean podeGerenciarCiclos(Long idCiclo) {
        UsuarioAutenticado usuarioAuth = atual();
        if (!podeGerenciar()) return false;

        Ciclo ciclo = cicloRepo.findById(idCiclo).orElseThrow(() -> new ModelNotFoundException("Ciclo"));
        return Objects.equals(usuarioAuth.idEmpresa(), ciclo.getEmpresa().getId());
    }
}
