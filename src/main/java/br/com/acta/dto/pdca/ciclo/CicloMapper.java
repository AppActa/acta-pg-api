package br.com.acta.dto.pdca.ciclo;

import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.dto.mapper.base.AuditoriaBaseMapper;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloMapper;
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
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    Ciclo toEntity(CicloRequestDTO dto);

    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "gestor", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataFimReal", ignore = true)
    @Mapping(target = "colaboradores", ignore = true)
    @Mapping(target = "idIshikawaMongo", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    void updateEntity(CicloRequestDTO dto, @MappingTarget Ciclo ciclo);
}
