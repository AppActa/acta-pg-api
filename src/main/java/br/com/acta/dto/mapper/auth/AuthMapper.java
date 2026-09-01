package br.com.acta.dto.mapper.auth;

import br.com.acta.common.config.security.UsuarioAutenticado;
import br.com.acta.dto.auth.MeResponseDTO;
import br.com.acta.entity.core.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    MeResponseDTO toMeResponse(UsuarioAutenticado usuario);

    @Mapping(source = "id", target = "idUsuario")
    @Mapping(source = "empresa.id", target = "idEmpresa")
    @Mapping(source = "colaborador.id", target = "idColaborador")
    @Mapping(source = "emailLogin", target = "email")
    @Mapping(source = "empresa.nome", target = "nomeEmpresa")
    @Mapping(source = "colaborador.permissaoGestor", target = "permissaoGestor")
    UsuarioAutenticado toUsuarioAutenticado(Usuario usuario);
}
