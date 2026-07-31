package br.com.acta.mapper.base;

import br.com.acta.entity.base.auditoria.AuditoriaBase;

public interface SummaryBaseMapper<REQ, RESP, ENT extends AuditoriaBase, SUM>
extends AuditoriaBaseMapper<REQ, RESP, ENT> {
    SUM toSummary(ENT ent);
}
