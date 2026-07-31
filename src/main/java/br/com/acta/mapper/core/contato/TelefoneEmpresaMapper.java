package br.com.acta.mapper.core.contato;

import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.entity.core.contato.TelefoneEmpresa;
import br.com.acta.mapper.base.TelefoneBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TelefoneEmpresaMapper
extends TelefoneBaseMapper<TelefoneEmpresa> {
    @Mapping(target = "empresa", ignore = true)
    @Override
    TelefoneEmpresa toEntity(TelefoneRequestDTO dto);

    @Mapping(target = "empresa", ignore = true)
    @Override
    void updateEntity(TelefoneRequestDTO dto, @MappingTarget TelefoneEmpresa telefoneEmpresa);
}
