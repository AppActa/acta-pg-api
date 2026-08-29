package br.com.acta.common.config.security;

import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
public class FirebaseAuthFilter extends OncePerRequestFilter {
    private static final String AUTH = "/auth/ativar";
    private final FirebaseUtils utils;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return "/health".equals(request.getServletPath());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = utils.extrairToken(request);
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            FirebaseToken idToken = utils.verificarIdToken(token);

            if (ehRotaAutenticacao(request)) {
                Principal principal = new FirebaseIdentity(idToken.getUid(), idToken.getEmail(), idToken.isEmailVerified());
                utils.autenticar(request, principal, List.of(new SimpleGrantedAuthority("ROLE_FIREBASE")));
            } else {
                UsuarioAutenticado usuario = utils.extrairUsuario(idToken);
                utils.autenticar(request, usuario, usuario.authorities());
            }

            filterChain.doFilter(request, response);
        } catch (BadCredentialsException bce){
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, bce.getMessage());
        } catch (AccessDeniedException ade) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_FORBIDDEN, ade.getMessage());
        }
    }

    public record FirebaseIdentity(String firebaseUid, String email, boolean emailVerificado) implements Principal {
        @Override
        public String getName() {
            return firebaseUid;
        }
    }

    private boolean ehRotaAutenticacao(HttpServletRequest request) {
        return request.getServletPath().equals(AUTH) && "POST".equalsIgnoreCase(request.getMethod());
    }
}
