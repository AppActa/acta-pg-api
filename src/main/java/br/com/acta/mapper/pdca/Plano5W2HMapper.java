package br.com.acta.mapper.pdca;

import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HRequestDTO;
import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HResponseDTO;
import br.com.acta.entity.pdca.Plano5W2H;
import br.com.acta.mapper.base.AuditoriaBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface Plano5W2HMapper
extends AuditoriaBaseMapper<Plano5W2HRequestDTO, Plano5W2HResponseDTO, Plano5W2H> {
    @Mapping(source = "whoResponsavel.id", target = "idWhoResponsavel")
    @Mapping(source = "planoAcao.id", target = "idPlanoAcao")
    Plano5W2HResponseDTO toResponse(Plano5W2H plano5W2H);

    @Mapping(target = "whoResponsavel", ignore = true)
    @Mapping(target = "planoAcao", ignore = true)
    Plano5W2H toEntity(Plano5W2HRequestDTO dto);

    @Mapping(target = "whoResponsavel", ignore = true)
    @Mapping(target = "planoAcao", ignore = true)
    void updateEntity(Plano5W2HRequestDTO dto, @MappingTarget Plano5W2H plano5W2H);
}
