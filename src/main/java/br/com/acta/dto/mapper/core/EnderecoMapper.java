package br.com.acta.dto.mapper.core;

import br.com.acta.common.utils.Formatador;
import br.com.acta.dto.core.empresa.endereco.EnderecoRequestDTO;
import br.com.acta.dto.core.empresa.endereco.EnderecoResponseDTO;
import br.com.acta.entity.core.Endereco;
import br.com.acta.dto.mapper.base.AuditoriaBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {Formatador.class})
public interface EnderecoMapper
extends AuditoriaBaseMapper<EnderecoRequestDTO, EnderecoResponseDTO, Endereco> {
    @Mapping(source = "cep", target = "cep", qualifiedByName = "formatarCep")
    @Override
    EnderecoResponseDTO toResponse(Endereco endereco);

    @Mapping(target = "empresa", ignore = true)
    @Override
    Endereco toEntity(EnderecoRequestDTO dto);

    @Mapping(target = "empresa", ignore = true)
    @Override
    void updateEntity(EnderecoRequestDTO dto, @MappingTarget Endereco endereco);
}
