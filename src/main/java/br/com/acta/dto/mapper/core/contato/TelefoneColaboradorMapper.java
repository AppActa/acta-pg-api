package br.com.acta.dto.mapper.core.contato;

import br.com.acta.common.utils.Formatador;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.entity.core.contato.TelefoneColaborador;
import br.com.acta.dto.mapper.base.TelefoneBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {Formatador.class})
public interface TelefoneColaboradorMapper
extends TelefoneBaseMapper<TelefoneColaborador> {
    @Mapping(source = "contato", target = "numero", qualifiedByName = "formatarTelefone")
    @Override
    TelefoneResponseDTO toResponse(TelefoneColaborador telefoneColaborador);

    @Mapping(source = "numero", target = "contato")
    @Mapping(target = "colaborador", ignore = true)
    @Override
    TelefoneColaborador toEntity(TelefoneRequestDTO dto);

    @Mapping(source = "numero", target = "contato")
    @Mapping(target = "colaborador", ignore = true)
    @Override
    void updateEntity(TelefoneRequestDTO dto, @MappingTarget TelefoneColaborador telefoneColaborador);
}
