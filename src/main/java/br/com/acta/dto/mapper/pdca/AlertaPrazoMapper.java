package br.com.acta.dto.mapper.pdca;

import br.com.acta.dto.pdca.alerta_prazo.AlertaPrazoResponseDTO;
import br.com.acta.entity.pdca.AlertaPrazo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AlertaPrazoMapper{
    @Mapping(source = "criadoEm", target = "enviadoEm")
    @Mapping(source = "tarefa.id", target = "idTarefa")
    @Mapping(source = "tarefa.titulo", target = "tituloTarefa")
    @Mapping(source = "usuarioDestino.id", target = "idUsuarioDestino")
    AlertaPrazoResponseDTO toResponse(AlertaPrazo alertaPrazo);

    default List<AlertaPrazoResponseDTO> toResponseList(Collection<AlertaPrazo> alertasPrazo) {
        return alertasPrazo
                .stream()
                .map(this::toResponse)
                .toList();
    }
}
