package br.com.acta.mapper.base;

import br.com.acta.entity.base.auditoria.AuditoriaBase;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface AuditoriaBaseMapper<REQ, RESP, ENT extends AuditoriaBase>
extends BaseMapper<REQ, RESP, ENT> {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    RESP toResponse(ENT ent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    void updateEntity(REQ dto, @MappingTarget ENT ent);
}
