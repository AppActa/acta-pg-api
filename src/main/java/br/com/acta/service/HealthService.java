package br.com.acta.service;

import br.com.acta.dto.health.HealthResponseDTO;
import br.com.acta.dto.health.HealthStatus;
import br.com.acta.common.client.AuthApiClient;
import br.com.acta.common.client.FcmApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class HealthService {
    private final JdbcTemplate jdbcTemplate;
    private final AuthApiClient authApiClient;
    private final FcmApiClient fcmApiClient;

    public HealthResponseDTO verificar() {
        HealthStatus banco = verificarBanco();
        HealthStatus auth = authApiClient.verificarSaude() ? HealthStatus.UP : HealthStatus.DOWN;
        HealthStatus fcm = fcmApiClient.verificarSaude() ? HealthStatus.UP : HealthStatus.DOWN;
        HealthStatus status = banco == HealthStatus.UP && auth == HealthStatus.UP && fcm == HealthStatus.UP ? HealthStatus.UP : HealthStatus.DOWN;
        String mensagem = status == HealthStatus.UP ? "O CATO verificou: API, banco de dados, Auth API e FCM estão funcionando!" : "O CATO verificou: Um ou mais componentes estão indisponíveis...";
        return new HealthResponseDTO(status, banco, auth, fcm, mensagem, OffsetDateTime.now());
    }

    private HealthStatus verificarBanco() {
        try { testarBanco(); return HealthStatus.UP; }
        catch (Exception e) { return HealthStatus.DOWN; }
    }

    private void testarBanco() {
        Integer resultado = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        if (resultado == null || resultado != 1) throw new RuntimeException("Erro ao testar o banco");
    }
}