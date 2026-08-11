package br.com.acta.dto.mapper.pdca;

import br.com.acta.dto.pdca.treinamento.TreinamentoRequestDTO;
import br.com.acta.dto.pdca.treinamento.TreinamentoResponseDTO;
import br.com.acta.entity.pdca.Treinamento;
import br.com.acta.dto.mapper.base.AuditoriaBaseMapper;
import br.com.acta.dto.mapper.join.UsuarioTreinamentoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {UsuarioTreinamentoMapper.class})
public interface TreinamentoMapper
extends AuditoriaBaseMapper<TreinamentoRequestDTO, TreinamentoResponseDTO, Treinamento> {
    @Mapping(source = "ciclo.id", target = "idCiclo")
    @Mapping(source = "responsavel.id", target = "idResponsavel")
    @Mapping(source = "responsavel.nome", target = "nomeResponsavel")
    @Override
    TreinamentoResponseDTO toResponse(Treinamento treinamento);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "responsavel", ignore = true)
    @Override
    Treinamento toEntity(TreinamentoRequestDTO dto);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "responsavel", ignore = true)
    @Override
    void updateEntity(TreinamentoRequestDTO dto, @MappingTarget Treinamento treinamento);
}
