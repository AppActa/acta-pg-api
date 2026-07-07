package br.com.acta.mapper.core;

import br.com.acta.dto.core.colaborador.ColaboradorRequestDTO;
import br.com.acta.dto.core.colaborador.ColaboradorResponseDTO;
import br.com.acta.entity.core.Colaborador;
import br.com.acta.mapper.base.AuditoriaBaseMapper;
import br.com.acta.mapper.core.contato.EmailColaboradorMapper;
import br.com.acta.mapper.core.contato.TelefoneColaboradorMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EmailColaboradorMapper.class, TelefoneColaboradorMapper.class})
public interface ColaboradorMapper
extends AuditoriaBaseMapper<ColaboradorRequestDTO, ColaboradorResponseDTO, Colaborador> {
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "empresa.id", target = "idEmpresa")
    @Override
    ColaboradorResponseDTO toResponse(Colaborador colaborador);

    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Override
    Colaborador toEntity(ColaboradorRequestDTO dto);

    @Mapping(target = "empresa", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Override
    void updateEntity(ColaboradorRequestDTO dto, @MappingTarget Colaborador colaborador);

    @AfterMapping
    default void link(@MappingTarget Colaborador colaborador){
        colaborador.getEmails().forEach(e -> e.setColaborador(colaborador));
        colaborador.getTelefones().forEach(t -> t.setColaborador(colaborador));
    }
}
