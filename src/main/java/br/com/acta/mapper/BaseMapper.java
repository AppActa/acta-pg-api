package br.com.acta.mapper;

import java.util.List;

public interface BaseMapper<REQ, RESP, ENT> {
    ENT toEntity(REQ dto);
    RESP toResponse(ENT ent);

    default List<RESP> toResponseList(List<ENT> entList){
        return entList.stream()
                .map(this::toResponse)
                .toList();
    }
}
