package br.com.acta.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta do login")
public record LoginResponseDTO(
        @Schema(description = "Token JWT de autenticação", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c3VhcmlvQGVtYWlsLmNvbSIsImlhdCI6MTYwOTQyMzYwMCwiZXhwIjoxNjA5NDI3MjAwfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
        String token,

        @Schema(description = "Tipo de usuário", example = "ADMIN")
        String tipo,

        @Schema(description = "Timestamp de expiração do token", example = "3600")
        Long expiraEm
) {
}
