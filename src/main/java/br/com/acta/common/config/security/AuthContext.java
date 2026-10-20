package br.com.acta.common.config.security;

import br.com.acta.entity.enums.TipoUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public record AuthContext(
        Long idUsuario,
        Long idEmpresa,
        Long idColaborador,
        TipoUsuario tipo,
        boolean permissaoGestor
) implements Principal {
    public List<GrantedAuthority> authorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + tipo.name()));
        if (permissaoGestor) authorities.add(new SimpleGrantedAuthority("PERMISSAO_GESTOR"));
        return authorities;
    }

    @Override
    public String getName() {
        return idUsuario.toString();
    }
}