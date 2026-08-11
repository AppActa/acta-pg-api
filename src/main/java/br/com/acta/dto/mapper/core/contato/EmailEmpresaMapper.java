package br.com.acta.dto.mapper.core.contato;

import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.mapper.base.EmailBaseMapper;
import br.com.acta.entity.core.contato.EmailEmpresa;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmailEmpresaMapper extends EmailBaseMapper<EmailEmpresa> {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "contato", source = "email")
    @Mapping(target = "empresa", ignore = true)
    @Override
    EmailEmpresa toEntity(EmailRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "contato", source = "email")
    @Mapping(target = "empresa", ignore = true)
    @Override
    void updateEntity(EmailRequestDTO dto, @MappingTarget EmailEmpresa emailEmpresa);
}