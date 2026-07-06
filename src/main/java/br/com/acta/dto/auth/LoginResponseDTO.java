package br.com.acta.dto.auth;


public record LoginResponseDTO(
        String token,
        String tipo,
        Long expiraEm
) {
}
