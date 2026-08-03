package br.com.acta.dto.mapper.base;

import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

public interface BaseMapper<REQ, RESP, ENT> {
    ENT toEntity(REQ dto);
    RESP toResponse(ENT ent);
    void updateEntity(REQ dto, @MappingTarget ENT ent);

    default List<RESP> toResponseList(Collection<ENT> entList){
        if (entList == null) return null;
        return entList.stream()
                .map(this::toResponse)
                .toList();
    }
}
