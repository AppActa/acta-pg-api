package br.com.acta.mapper.pdca;

import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioRequestDTO;
import br.com.acta.dto.pdca.efeito_secundario.EfeitoSecundarioResponseDTO;
import br.com.acta.entity.pdca.EfeitoSecundario;
import br.com.acta.mapper.base.AuditoriaBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EfeitoSecundarioMapper
extends AuditoriaBaseMapper<EfeitoSecundarioRequestDTO, EfeitoSecundarioResponseDTO, EfeitoSecundario> {
    @Mapping(source = "verificacaoResultado.id", target = "idVerificacaoResultado")
    @Override
    EfeitoSecundarioResponseDTO toResponse(EfeitoSecundario efeitoSecundario);

    @Mapping(target = "verificacaoResultado", ignore = true)
    @Override
    EfeitoSecundario toEntity(EfeitoSecundarioRequestDTO dto);

    @Mapping(target = "verificacaoResultado", ignore = true)
    @Override
    void updateEntity(EfeitoSecundarioRequestDTO dto, @MappingTarget EfeitoSecundario efeitoSecundario);
}
