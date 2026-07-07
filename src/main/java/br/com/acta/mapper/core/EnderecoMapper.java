package br.com.acta.mapper.core;

import br.com.acta.dto.core.empresa.endereco.EnderecoRequestDTO;
import br.com.acta.dto.core.empresa.endereco.EnderecoResponseDTO;
import br.com.acta.entity.core.Endereco;
import br.com.acta.mapper.base.AuditoriaBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EnderecoMapper
extends AuditoriaBaseMapper<EnderecoRequestDTO, EnderecoResponseDTO, Endereco> {
    @Mapping(target = "empresa", ignore = true)
    @Override
    Endereco toEntity(EnderecoRequestDTO dto);

    @Mapping(target = "empresa", ignore = true)
    @Override
    void updateEntity(EnderecoRequestDTO dto, @MappingTarget Endereco endereco);
}
