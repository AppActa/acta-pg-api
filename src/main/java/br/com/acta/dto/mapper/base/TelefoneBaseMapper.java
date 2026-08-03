package br.com.acta.dto.mapper.base;

import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.entity.base.ContatoBase;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface TelefoneBaseMapper<ENT extends ContatoBase>
extends BaseMapper<TelefoneRequestDTO, TelefoneResponseDTO, ENT> {
    @Override
    TelefoneResponseDTO toResponse(ENT ent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "contato", source = "numero")
    @Override
    ENT toEntity(TelefoneRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "contato", source = "numero")
    @Override
    void updateEntity(TelefoneRequestDTO dto, @MappingTarget ENT ent);
}
