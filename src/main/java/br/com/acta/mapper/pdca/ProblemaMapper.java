package br.com.acta.mapper.pdca;

import br.com.acta.dto.pdca.problema.ProblemaRequestDTO;
import br.com.acta.dto.pdca.problema.ProblemaResponseDTO;
import br.com.acta.dto.pdca.problema.ProblemaSummaryResponseDTO;
import br.com.acta.entity.pdca.Problema;
import br.com.acta.mapper.base.SummaryBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProblemaMapper
extends SummaryBaseMapper<ProblemaRequestDTO, ProblemaResponseDTO, Problema, ProblemaSummaryResponseDTO> {
    @Mapping(source = "problemaPai.id", target = "idProblemaPai")
    @Mapping(source = "ciclo.id", target = "idCiclo")
    @Mapping(source = "criadoPor.id", target = "idCriadoPor")
    @Override
    ProblemaResponseDTO toResponse(Problema problema);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    @Mapping(target = "problemaPai", ignore = true)
    @Override
    Problema toEntity(ProblemaRequestDTO dto);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    @Mapping(target = "problemaPai", ignore = true)
    @Override
    void updateEntity(ProblemaRequestDTO dto, @MappingTarget Problema problema);
}
