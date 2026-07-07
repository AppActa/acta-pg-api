package br.com.acta.mapper.pdca;

import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoRequestDTO;
import br.com.acta.dto.pdca.verificacao_resultado.VerificacaoResultadoResponseDTO;
import br.com.acta.entity.pdca.VerificacaoResultado;
import br.com.acta.mapper.base.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VerificacaoResultadoMapper
extends BaseMapper<VerificacaoResultadoRequestDTO, VerificacaoResultadoResponseDTO, VerificacaoResultado> {
    @Mapping(source = "ciclo.id", target = "idCiclo")
    @Mapping(source = "criadoPor.id", target = "idCriadoPor")
    @Override
    VerificacaoResultadoResponseDTO toResponse(VerificacaoResultado verificacaoResultado);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    @Mapping(target = "efeitosSecundarios", ignore = true)
    @Override
    VerificacaoResultado toEntity(VerificacaoResultadoRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    @Mapping(target = "efeitosSecundarios", ignore = true)
    @Override
    void updateEntity(VerificacaoResultadoRequestDTO dto, @MappingTarget VerificacaoResultado verificacaoResultado);
}
