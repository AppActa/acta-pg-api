package br.com.acta.service.base;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.dto.mapper.base.BaseMapper;
import br.com.acta.repository.base.BaseRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class BaseService<REQ, RESP, ENT>
implements BaseCRUD<REQ, RESP> {
    protected final BaseRepository<ENT> repo;
    protected final BaseMapper<REQ, RESP, ENT> mapper;
    protected final Class<ENT> classeENT;

    protected void antesInserir(ENT ent, REQ dto){}

    public ENT getEntity(Long id){
        return repo.findById(id).orElseThrow(() -> new ModelNotFoundException(classeENT.getSimpleName(), id));
    }

    @Override
    public RESP buscar(Long id) {
        ENT ent = getEntity(id);
        return mapper.toResponse(ent);
    }

    @Override
    public List<RESP> buscar() {
        List<ENT> entList = repo.findAll();

        return mapper.toResponseList(entList);
    }

    @Override
    public RESP inserir(REQ dto) {
        ENT ent = mapper.toEntity(dto);
        antesInserir(ent, dto);

        ENT salvo = repo.save(ent);
        return mapper.toResponse(salvo);
    }

    @Override
    abstract public RESP patch(Long id, Map<String, Object> campos);

    @Override
    public void excluir(Long id) {
        ENT ent = getEntity(id);
        repo.delete(ent);
    }
}
