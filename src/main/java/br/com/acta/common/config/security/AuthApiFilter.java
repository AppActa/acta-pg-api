package br.com.acta.common.config.security;

import br.com.acta.common.client.AuthApiClient;
import br.com.acta.common.handler.ErroResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import static org.springframework.util.StringUtils.startsWithIgnoreCase;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class AuthApiFilter extends OncePerRequestFilter {
    private final AuthApiClient authApiClient;
    private final ObjectMapper mapper;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return "/api/v1/health".equals(path)
                || "/images/acta-logo.png".equals(path)
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!startsWithIgnoreCase(authorization, "Bearer ") || authorization.substring(7).isBlank()) {
            responder(response, HttpStatus.UNAUTHORIZED, "Token ausente ou inválido");
            return;
        }

        AuthContext contexto;
        try {
            contexto = authApiClient.contexto(authorization);
            if (contexto == null || contexto.idUsuario() == null || contexto.idEmpresa() == null || contexto.tipo() == null) {
                responder(response, HttpStatus.SERVICE_UNAVAILABLE, "A autenticação retornou um contexto inválido");
                return;
            }
        } catch (RestClientResponseException e) {
            HttpStatus status = switch (e.getStatusCode().value()) {
                case 401 -> HttpStatus.UNAUTHORIZED;
                case 403 -> HttpStatus.FORBIDDEN;
                default -> HttpStatus.SERVICE_UNAVAILABLE;
            };

            responder(response, status, mensagem(status));
            return;
        } catch (RestClientException e) {
            responder(response, HttpStatus.SERVICE_UNAVAILABLE, mensagem(HttpStatus.SERVICE_UNAVAILABLE));
            return;
        }

        UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.authenticated(contexto, null, contexto.authorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);

        SecurityContextHolder.setContext(securityContext);
        chain.doFilter(request, response);
    }

    private void responder(HttpServletResponse response, HttpStatus status, String message) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        mapper.writeValue(response.getOutputStream(), new ErroResponse(List.of(message), status.value()));
    }

    private String mensagem(HttpStatus status) {
        return switch (status) {
            case UNAUTHORIZED -> "Token ausente ou inválido";
            case FORBIDDEN -> "Acesso negado";
            default -> "A API de autenticação está indisponível";
        };
    }
}