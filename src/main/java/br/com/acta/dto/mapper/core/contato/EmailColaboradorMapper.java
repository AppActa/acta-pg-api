package br.com.acta.dto.mapper.core.contato;

import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.mapper.base.EmailBaseMapper;
import br.com.acta.entity.core.contato.EmailColaborador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmailColaboradorMapper extends EmailBaseMapper<EmailColaborador> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "contato", source = "email")
    @Mapping(target = "colaborador", ignore = true)
    @Override
    EmailColaborador toEntity(EmailRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "contato", source = "email")
    @Mapping(target = "colaborador", ignore = true)
    @Override
    void updateEntity(EmailRequestDTO dto, @MappingTarget EmailColaborador emailColaborador);
}