package br.com.acta.dto.pdca.meta;

import br.com.acta.entity.pdca.Meta;
import br.com.acta.dto.mapper.base.AuditoriaBaseMapper;
import br.com.acta.dto.core.usuario.UsuarioMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class})
public interface MetaMapper
extends AuditoriaBaseMapper<MetaRequestDTO, MetaResponseDTO, Meta> {
    @Mapping(source = "ciclo.id", target = "idCiclo")
    @Mapping(source = "planoAcao.id", target = "idPlanoAcao")
    @Override
    MetaResponseDTO toResponse(Meta meta);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "planoAcao", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "responsaveis", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    Meta toEntity(MetaRequestDTO dto);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "planoAcao", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "responsaveis", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    void updateEntity(MetaRequestDTO dto, @MappingTarget Meta meta);
}
