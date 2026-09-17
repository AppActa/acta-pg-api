package br.com.acta.dto.core.convite;

import br.com.acta.dto.mapper.base.BaseMapper;
import br.com.acta.entity.core.Convite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ConviteMapper extends BaseMapper<ConviteRequestDTO, ConviteResponseDTO, Convite> {
    @Mapping(source = "usuarioDestino.id", target = "idUsuarioDestino")
    @Mapping(source = "criadoPor.id", target = "idCriadoPor")
    @Override
    ConviteResponseDTO toResponse(Convite convite);

    @Mapping(target = "usuarioDestino", ignore = true)
    @Mapping(source = "email", target = "emailDestino")
    @Mapping(target = "tokenHash", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "usadoEm", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "expiraEm", ignore = true)
    @Override
    Convite toEntity(ConviteRequestDTO dto);

    @Mapping(target = "usuarioDestino", ignore = true)
    @Mapping(source = "email", target = "emailDestino")
    @Mapping(target = "tokenHash", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "usadoEm", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "expiraEm", ignore = true)
    @Override
    void updateEntity(ConviteRequestDTO dto, @MappingTarget Convite convite);
}
