package br.com.acta.mapper.pdca;

import br.com.acta.dto.pdca.ciclo.CicloRequestDTO;
import br.com.acta.dto.pdca.ciclo.CicloResponseDTO;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.mapper.base.AuditoriaBaseMapper;
import br.com.acta.mapper.join.UsuarioCicloMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UsuarioCicloMapper.class})
public interface CicloMapper
extends AuditoriaBaseMapper<CicloRequestDTO, CicloResponseDTO, Ciclo> {
    @Mapping(source = "empresa.id", target = "idEmpresa")
    @Mapping(source = "gestor.id", target = "idGestor")
    @Override
    CicloResponseDTO toResponse(Ciclo ciclo);

    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "gestor", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataFimReal", ignore = true)
    @Mapping(target = "colaboradores", ignore = true)
    @Mapping(target = "idIshikawaMongo", ignore = true)
    @Override
    Ciclo toEntity(CicloRequestDTO dto);

    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "gestor", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataFimReal", ignore = true)
    @Mapping(target = "colaboradores", ignore = true)
    @Mapping(target = "idIshikawaMongo", ignore = true)
    @Override
    void updateEntity(CicloRequestDTO dto, @MappingTarget Ciclo ciclo);
}
