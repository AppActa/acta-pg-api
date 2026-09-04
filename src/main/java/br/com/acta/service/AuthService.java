package br.com.acta.service;

import br.com.acta.common.config.security.FirebaseAuthFilter.FirebaseIdentity;
import br.com.acta.common.config.security.FirebaseUtils;
import br.com.acta.common.config.security.UsuarioAutenticado;
import br.com.acta.common.handler.exception.FirebaseAccessRevokedException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.dto.auth.MeResponseDTO;
import br.com.acta.dto.mapper.auth.AuthMapper;
import br.com.acta.entity.core.Usuario;
import br.com.acta.repository.padrao.ColaboradorRepository;
import br.com.acta.repository.padrao.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository repo;
    private final AuthMapper mapper;
    private final FirebaseUtils utils;
    private final UsuarioRepository usuarioRepo;
    private final ColaboradorRepository colaboradorRepo;

    @PreAuthorize("hasAuthority('ROLE_FIREBASE')")
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

    public UsuarioAutenticado atual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UsuarioAutenticado usuario) || !auth.isAuthenticated())
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado");

        return usuario;
    }

    public boolean isProprioUsuario(Long idUsuario) {
        return Objects.equals(atual().idUsuario(), idUsuario);
    }

    public boolean isUsuarioEmpresa(Long idUsuario) {
        return usuarioRepo.existsByIdAndEmpresaId(idUsuario, atual().idEmpresa());
    }

    public boolean isColaboradorEmpresa(Long idColaborador) {
        return colaboradorRepo.existsByIdAndEmpresaId(idColaborador, atual().idEmpresa());
    }
}
