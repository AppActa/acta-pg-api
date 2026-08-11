package br.com.acta.dto.mapper.join;

import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloRequestDTO;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloResponseDTO;
import br.com.acta.entity.join.UsuarioCiclo;
import br.com.acta.dto.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioCicloMapper
extends BaseMapper<UsuarioCicloRequestDTO, UsuarioCicloResponseDTO, UsuarioCiclo> {
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "ciclo.id", target = "idCiclo")
    @Mapping(source = "usuario.nome", target = "nomeUsuario")
    @Override
    UsuarioCicloResponseDTO toResponse(UsuarioCiclo usuarioCiclo);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "ciclo", ignore = true)
    @Override
    UsuarioCiclo toEntity(UsuarioCicloRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "ciclo", ignore = true)
    @Override
    void updateEntity(UsuarioCicloRequestDTO dto, @MappingTarget UsuarioCiclo usuarioCiclo);
}