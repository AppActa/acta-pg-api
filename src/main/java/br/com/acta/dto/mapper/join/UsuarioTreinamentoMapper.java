package br.com.acta.dto.mapper.join;

import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoRequestDTO;
import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoResponseDTO;
import br.com.acta.entity.join.UsuarioTreinamento;
import br.com.acta.dto.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UsuarioTreinamentoMapper
extends BaseMapper<UsuarioTreinamentoRequestDTO, UsuarioTreinamentoResponseDTO, UsuarioTreinamento> {
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "usuario.nome", target = "nomeUsuario")
    @Override
    UsuarioTreinamentoResponseDTO toResponse(UsuarioTreinamento usuarioTreinamento);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "treinamento", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "terminadoEm", ignore = true)
    @Override
    UsuarioTreinamento toEntity(UsuarioTreinamentoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "treinamento", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "terminadoEm", ignore = true)
    @Override
    void updateEntity(UsuarioTreinamentoRequestDTO dto, @MappingTarget UsuarioTreinamento usuarioTreinamento);
}
