package br.com.acta.mapper.pdca;

import br.com.acta.dto.pdca.causa_raiz.CausaRaizRequestDTO;
import br.com.acta.dto.pdca.causa_raiz.CausaRaizResponseDTO;
import br.com.acta.entity.pdca.CausaRaiz;
import br.com.acta.mapper.base.AuditoriaBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CausaRaizMapper
extends AuditoriaBaseMapper<CausaRaizRequestDTO, CausaRaizResponseDTO, CausaRaiz> {
    @Mapping(source = "problema.id", target = "idProblema")
    @Mapping(source = "ciclo.id", target = "idCiclo")
    @Mapping(source = "planoAcao.id", target = "idPlanoAcao")
    @Mapping(source = "validadaPor.id", target = "idValidadaPor")
    @Override
    CausaRaizResponseDTO toResponse(CausaRaiz causaRaiz);

    @Mapping(target = "aceita", ignore = true)
    @Mapping(target = "validadaEm", ignore = true)
    @Mapping(target = "problema", ignore = true)
    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "planoAcao", ignore = true)
    @Mapping(target = "validadaPor", ignore = true)
    @Mapping(target = "id5PorquesMongo", ignore = true)
    @Override
    CausaRaiz toEntity(CausaRaizRequestDTO dto);

    @Mapping(target = "aceita", ignore = true)
    @Mapping(target = "validadaEm", ignore = true)
    @Mapping(target = "problema", ignore = true)
    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "planoAcao", ignore = true)
    @Mapping(target = "validadaPor", ignore = true)
    @Mapping(target = "id5PorquesMongo", ignore = true)
    @Override
    void updateEntity(CausaRaizRequestDTO dto, @MappingTarget CausaRaiz causaRaiz);
}
