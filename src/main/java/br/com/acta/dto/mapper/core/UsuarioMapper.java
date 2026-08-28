package br.com.acta.dto.mapper.core;

import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioResponseDTO;
import br.com.acta.dto.core.usuario.UsuarioSummaryResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.dto.mapper.base.SummaryBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioMapper
extends SummaryBaseMapper<UsuarioRequestDTO, UsuarioResponseDTO, Usuario, UsuarioSummaryResponseDTO> {
    @Mapping(source = "emailLogin", target = "email")
    @Mapping(source = "empresa.id", target = "idEmpresa")
    @Override
    UsuarioResponseDTO toResponse(Usuario usuario);

    @Mapping(target = "emailLogin", source = "email")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "colaborador", ignore = true)
    @Mapping(target = "ciclos", ignore = true)
    @Mapping(target = "metas", ignore = true)
    @Mapping(target = "firebaseUid", ignore = true)
    @Override
    Usuario toEntity(UsuarioRequestDTO dto);

    @Mapping(target = "emailLogin", source = "email")
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "colaborador", ignore = true)
    @Mapping(target = "ciclos", ignore = true)
    @Mapping(target = "metas", ignore = true)
    @Mapping(target = "firebaseUid", ignore = true)
    @Override
    void updateEntity(UsuarioRequestDTO dto, @MappingTarget Usuario usuario);
}
