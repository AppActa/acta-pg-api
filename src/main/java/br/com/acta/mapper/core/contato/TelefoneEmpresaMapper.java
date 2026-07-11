package br.com.acta.mapper.core.contato;

import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.entity.core.contato.TelefoneEmpresa;
import br.com.acta.mapper.base.TelefoneBaseMapper;
import br.com.acta.utils.Formatador;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {Formatador.class})
public interface TelefoneEmpresaMapper
extends TelefoneBaseMapper<TelefoneEmpresa> {
    @Mapping(source = "contato", target = "numero", qualifiedByName = "formatarTelefone")
    @Override
    TelefoneResponseDTO toResponse(TelefoneEmpresa telefoneEmpresa);

    @Mapping(target = "empresa", ignore = true)
    @Override
    TelefoneEmpresa toEntity(TelefoneRequestDTO dto);

    @Mapping(target = "empresa", ignore = true)
    @Override
    void updateEntity(TelefoneRequestDTO dto, @MappingTarget TelefoneEmpresa telefoneEmpresa);
}
