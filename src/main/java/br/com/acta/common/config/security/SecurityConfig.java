package br.com.acta.common.config.security;

import br.com.acta.common.handler.ErroResponse;
import br.com.acta.repository.padrao.UsuarioRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Configuration
@EnableWebSecurity //
@EnableMethodSecurity //
public class SecurityConfig {
    @Bean(destroyMethod = "delete")
    public FirebaseApp app(@Value("${firebase.project-id}") String idProjeto) throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.getApplicationDefault())
                .setProjectId(idProjeto)
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth auth(FirebaseApp app) {
        return FirebaseAuth.getInstance(app);
    }

    @Bean
    public FirebaseUtils utils(FirebaseAuth auth, UsuarioRepository repo) {
        return new FirebaseUtils(auth, repo);
    }

    @Bean
    public FirebaseAuthFilter filter(FirebaseUtils utils) {
        return new FirebaseAuthFilter(utils);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, FirebaseAuthFilter filter, ObjectMapper mapper) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, resp, e) ->
                                erro(mapper, resp, HttpStatus.UNAUTHORIZED, "O ID Token do Firebase não existe ou está inválido")) 
                        .accessDeniedHandler((req, resp, e) ->
                                erro(mapper, resp, HttpStatus.FORBIDDEN, "Acesso negado"))
                )
                .authorizeHttpRequests(a -> a
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.GET, "/health").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/ativar").hasAuthority("ROLE_FIREBASE")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private void erro(ObjectMapper mapper, HttpServletResponse resp, HttpStatus status, String msg) throws IOException {
        resp.setStatus(status.value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        mapper.writeValue(resp.getOutputStream(), new ErroResponse(List.of(msg), status.value()));
    }
}
