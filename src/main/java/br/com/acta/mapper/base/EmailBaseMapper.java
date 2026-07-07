package br.com.acta.mapper.base;

import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import br.com.acta.entity.base.ContatoBase;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

public interface EmailBaseMapper<ENT extends ContatoBase>
extends BaseMapper<EmailRequestDTO, EmailResponseDTO, ENT> {
    @Mapping(source = "contato", target = "email")
    @Override
    EmailResponseDTO toResponse(ENT ent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "contato", source = "email")
    @Override
    ENT toEntity(EmailRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "contato", source = "email")
    @Override
    void updateEntity(EmailRequestDTO dto, @MappingTarget ENT ent);
}
