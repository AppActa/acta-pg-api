package br.com.acta.dto.pdca.plano_acao;

import br.com.acta.dto.pdca.tarefa.TarefaMapper;
import br.com.acta.dto.pdca.plano_5w2h.Plano5W2HMapper;
import br.com.acta.entity.pdca.PlanoAcao;
import br.com.acta.dto.mapper.base.AuditoriaBaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {Plano5W2HMapper.class, TarefaMapper.class})
public interface PlanoAcaoMapper
extends AuditoriaBaseMapper<PlanoAcaoRequestDTO, PlanoAcaoResponseDTO, PlanoAcao> {
    @Mapping(source = "ciclo.id", target = "idCiclo")
    @Mapping(source = "criadoPor.id", target = "idCriadoPor")
    @Override
    PlanoAcaoResponseDTO toResponse(PlanoAcao planoAcao);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    @Mapping(target = "plano5W2H", ignore = true)
    @Mapping(target = "tarefas", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    PlanoAcao toEntity(PlanoAcaoRequestDTO dto);

    @Mapping(target = "ciclo", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "criadoPor", ignore = true)
    @Mapping(target = "plano5W2H", ignore = true)
    @Mapping(target = "tarefas", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "criadoEm", ignore = true)
    @Mapping(target = "atualizadoEm", ignore = true)
    @Override
    void updateEntity(PlanoAcaoRequestDTO dto, @MappingTarget PlanoAcao planoAcao);
}
