package br.com.acta.dto.mapper.base;

import br.com.acta.entity.base.auditoria.AuditoriaBase;

import java.util.Collection;
import java.util.List;

public interface SummaryBaseMapper<REQ, RESP, ENT extends AuditoriaBase, SUM>
extends AuditoriaBaseMapper<REQ, RESP, ENT> {
    SUM toSummary(ENT ent);

    default List<SUM> toSummaryList(Collection<ENT> entList) {
        return entList.stream().map(this::toSummary).toList();
    }
}
