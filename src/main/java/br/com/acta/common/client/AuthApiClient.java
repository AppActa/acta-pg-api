package br.com.acta.common.client;

import br.com.acta.common.config.security.AuthContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@RequiredArgsConstructor
public class AuthApiClient {
    private final RestClient authApiRestClient;

    public AuthContext contexto(String authorization) {
        return authApiRestClient.get()
                .uri("/api/v1/contexto")
                .header(HttpHeaders.AUTHORIZATION, authorization)
                .retrieve()
                .body(AuthContext.class);
    }
}