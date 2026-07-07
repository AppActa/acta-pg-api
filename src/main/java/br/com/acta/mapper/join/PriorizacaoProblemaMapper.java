package br.com.acta.mapper.join;

import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaRequestDTO;
import br.com.acta.dto.join.priorizacao_problema.PriorizacaoProblemaResponseDTO;
import br.com.acta.entity.join.PriorizacaoProblema;
import br.com.acta.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PriorizacaoProblemaMapper
extends BaseMapper<PriorizacaoProblemaRequestDTO, PriorizacaoProblemaResponseDTO, PriorizacaoProblema> {
    @Mapping(source = "problema.id", target = "idProblema")
    @Mapping(source = "problema.titulo", target = "tituloProblema")
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "usuario.nome", target = "nomeUsuario")
    @Override
    PriorizacaoProblemaResponseDTO toResponse(PriorizacaoProblema priorizacaoProblema);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "problema", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    PriorizacaoProblema toEntity(PriorizacaoProblemaRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "problema", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    void updateEntity(PriorizacaoProblemaRequestDTO dto, @MappingTarget PriorizacaoProblema priorizacaoProblema);
}
