package br.com.acta.service.base;

import java.util.List;
import java.util.Map;

public interface BaseCRUD<REQ, RESP> {
    RESP buscar(Long id);
    List<RESP> buscar();
    RESP inserir(REQ dto);
    RESP patch(Long id, Map<String, Object> campos);
    void excluir(Long id);
}
