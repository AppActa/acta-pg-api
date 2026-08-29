package br.com.acta.dto.auth;

import br.com.acta.common.config.security.UsuarioAutenticado;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TipoUsuario;

public record MeResponseDTO(
        String firebaseUid,
        Long idUsuario,
        Long idEmpresa,
        Long idColaborador,
        String nome,
        String email,
        String nomeEmpresa,
        TipoUsuario tipo,
        boolean permissaoGestor,
        StatusGeral status
) {
    public static MeResponseDTO from(UsuarioAutenticado usuario) {
        return new MeResponseDTO(
                usuario.firebaseUid(),
                usuario.idUsuario(),
                usuario.idEmpresa(),
                usuario.idColaborador(),
                usuario.nome(),
                usuario.email(),
                usuario.nomeEmpresa(),
                usuario.tipo(),
                usuario.permissaoGestor(),
                usuario.status()
        );
    }
}
