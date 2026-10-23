package br.com.acta.common.config.security;

import br.com.acta.common.client.AuthApiClient;
import br.com.acta.common.handler.ErroResponse;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;

@Configuration
@Profile("!test")
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Bean
    public RestClient authApiRestClient(
            @Value("${acta.auth-api.url}") String url,
            @Value("${acta.auth-api.connect-timeout:2s}") Duration connectTimeout,
            @Value("${acta.auth-api.read-timeout:3s}") Duration readTimeout) {
        HttpClient httpClient = HttpClient.newBuilder().connectTimeout(connectTimeout).build();
        JdkClientHttpRequestFactory factory = new JdkClientHttpRequestFactory(httpClient);
        factory.setReadTimeout(readTimeout);
        return RestClient.builder().baseUrl(url).requestFactory(factory).build();
    }

    @Bean
    public AuthApiClient authApiClient(RestClient authApiRestClient) {
        return new AuthApiClient(authApiRestClient);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthApiClient client, ObjectMapper mapper){
        http
                .cors(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, resp, e) -> erro(mapper, resp, HttpStatus.UNAUTHORIZED, "Token ausente ou inválido"))
                        .accessDeniedHandler((req, resp, e) -> erro(mapper, resp, HttpStatus.FORBIDDEN, "Acesso negado")))
                .authorizeHttpRequests(a -> a
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/health", "/images/acta-logo.png").permitAll()
                        .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new AuthApiFilter(client, mapper), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private void erro(ObjectMapper mapper, HttpServletResponse resp, HttpStatus status, String msg) throws IOException {
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        mapper.writeValue(resp.getOutputStream(), new ErroResponse(List.of(msg), status.value()));
    }
}