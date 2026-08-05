package br.com.acta.dto.mapper.pdca;

import br.com.acta.dto.pdca.tarefa.TarefaRequestDTO;
import br.com.acta.dto.pdca.tarefa.TarefaResponseDTO;
import br.com.acta.dto.pdca.tarefa.TarefaStatusUpdateDTO;
import br.com.acta.dto.pdca.tarefa.TarefaSummaryResponseDTO;
import br.com.acta.entity.pdca.Tarefa;
import br.com.acta.dto.mapper.base.SummaryBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TarefaMapper
extends SummaryBaseMapper<TarefaRequestDTO, TarefaResponseDTO, Tarefa, TarefaSummaryResponseDTO> {
    @Mapping(source = "planoAcao.id", target = "idPlanoAcao")
    @Mapping(source = "responsavel.id", target = "idResponsavel")
    @Mapping(source = "responsavel.nome", target = "nomeResponsavel")
    @Override
    TarefaResponseDTO toResponse(Tarefa tarefa);

    @Mapping(source = "responsavel.id", target = "idResponsavel")
    @Mapping(source = "responsavel.nome", target = "nomeResponsavel")
    TarefaSummaryResponseDTO toSummary(Tarefa tarefa);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataInicioReal", ignore = true)
    @Mapping(target = "dataFimReal", ignore = true)
    @Mapping(target = "planoAcao", ignore = true)
    @Mapping(target = "responsavel", ignore = true)
    @Mapping(target = "dependencias", ignore = true)
    @Mapping(target = "dependentes", ignore = true)
    @Override
    Tarefa toEntity(TarefaRequestDTO dto);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dataInicioReal", ignore = true)
    @Mapping(target = "dataFimReal", ignore = true)
    @Mapping(target = "planoAcao", ignore = true)
    @Mapping(target = "responsavel", ignore = true)
    @Mapping(target = "dependencias", ignore = true)
    @Mapping(target = "dependentes", ignore = true)
    @Override
    void updateEntity(TarefaRequestDTO dto, @MappingTarget Tarefa tarefa);

    @Mapping(target = "titulo", ignore = true)
    @Mapping(target = "descricao", ignore = true)
    @Mapping(target = "prioridade", ignore = true)
    @Mapping(target = "dataFimPrevista", ignore = true)
    @Mapping(target = "planoAcao", ignore = true)
    @Mapping(target = "responsavel", ignore = true)
    @Mapping(target = "dependencias", ignore = true)
    @Mapping(target = "dependentes", ignore = true)
    void updateStatus(TarefaStatusUpdateDTO dto, @MappingTarget Tarefa tarefa);
}