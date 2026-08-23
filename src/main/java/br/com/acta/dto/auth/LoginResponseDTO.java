package br.com.acta.dto.auth;


public record LoginResponseDTO(
        String token,
        Long idUsuario,
        String tipo,
        Long expiraEm
) {
}
