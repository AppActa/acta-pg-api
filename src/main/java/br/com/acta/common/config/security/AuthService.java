package br.com.acta.common.config.security;

import br.com.acta.entity.enums.TipoUsuario;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("authService")
public class AuthService {
    public UsuarioAutenticado atual() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof UsuarioAutenticado usuario) || !auth.isAuthenticated())
            throw new AuthenticationCredentialsNotFoundException("Usuário não autenticado");

        return usuario;
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
}
