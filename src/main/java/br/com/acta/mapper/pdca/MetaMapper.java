package br.com.acta.mapper.pdca;

import br.com.acta.dto.pdca.meta.MetaRequestDTO;
import br.com.acta.dto.pdca.meta.MetaResponseDTO;
import br.com.acta.entity.pdca.Meta;
import br.com.acta.mapper.base.AuditoriaBaseMapper;
import br.com.acta.mapper.core.UsuarioMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface MetaMapper
extends AuditoriaBaseMapper<MetaRequestDTO, MetaResponseDTO, Meta> {
    @Mapping(source = "ciclo.id", target = "idCiclo")
    @Override
    MetaResponseDTO toResponse(Meta meta);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "responsaveis", ignore = true)
    @Override
    Meta toEntity(MetaRequestDTO dto);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "responsaveis", ignore = true)
    @Override
    void updateEntity(MetaRequestDTO dto, @MappingTarget Meta meta);
}
