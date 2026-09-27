package br.com.acta.service;

import br.com.acta.common.config.firebase.UsuarioAutenticado;
import br.com.acta.repository.padrao.ColaboradorRepository;
import br.com.acta.repository.padrao.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UsuarioRepository repo;
    private final ColaboradorRepository colaboradorRepo;

    @PersistenceContext
    private EntityManager entityManager;

    public UsuarioAutenticado atual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UsuarioAutenticado usuario) || !auth.isAuthenticated())
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado");

        return usuario;
    }

    // só executa se existir uma transação aberta
    @Transactional(propagation = Propagation.MANDATORY)
    public void configurarUsuarioAtual() {
        UsuarioAutenticado usuario = atual();

        entityManager.createNativeQuery("SELECT set_config('app.current_user_id', :id, true)")
                .setParameter("id", usuario.idUsuario().toString())
                .getSingleResult();
    }

    public boolean isProprioUsuario(Long idUsuario) {
        return Objects.equals(atual().idUsuario(), idUsuario);
    }

    public boolean isUsuarioEmpresa(Long idUsuario) {
        return repo.existsByIdAndEmpresaId(idUsuario, atual().idEmpresa());
    }

    public boolean isColaboradorEmpresa(Long idColaborador) {
        return colaboradorRepo.existsByIdAndEmpresaId(idColaborador, atual().idEmpresa());
    }

    public boolean isUsuarioByIdEmpresa(Long idEmpresa) {
        return repo.existsByIdAndEmpresaId(atual().idUsuario(), idEmpresa);
    }
}