package br.com.acta.service;

import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoRequestDTO;
import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoResponseDTO;
import br.com.acta.entity.enums.StatusTreinamento;
import br.com.acta.entity.join.UsuarioTreinamento;
import br.com.acta.entity.pdca.Treinamento;
import br.com.acta.common.handler.exception.BusinessRuleException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.dto.mapper.join.UsuarioTreinamentoMapper;
import br.com.acta.repository.composto.UsuarioTreinamentoRepository;
import br.com.acta.common.utils.Validador;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioTreinamentoService {
    private final UsuarioTreinamentoRepository repo;
    private final UsuarioTreinamentoMapper mapper;
    private final TreinamentoService treinamentoService;

    public List<UsuarioTreinamentoResponseDTO> buscar(Long idTreinamento){
        List<UsuarioTreinamento> usuarios = repo.findByTreinamentoId(idTreinamento);
        return mapper.toResponseList(usuarios);
    }

    public UsuarioTreinamentoResponseDTO inserir(Long idTreinamento, UsuarioTreinamentoRequestDTO dto){
        Treinamento treinamento = treinamentoService.getEntity(idTreinamento);
        UsuarioTreinamento usuarioTreinamento = mapper.toEntity(dto);

        Validador.validarMesmoCiclo(treinamento.getCiclo(), usuarioTreinamento.getUsuario().getCiclos());
        if (repo.existsByUsuarioIdAndTreinamentoId(dto.idUsuario(), idTreinamento)) {
            throw new BusinessRuleException("Usuário já está inscrito neste treinamento");
        }

        usuarioTreinamento.setTreinamento(treinamento);
        UsuarioTreinamento salvo = repo.save(usuarioTreinamento);
        return mapper.toResponse(salvo);
    }

    public UsuarioTreinamentoResponseDTO patchStatus(Long idTreinamento, Long idUsuario, StatusTreinamento status){
        if (!repo.existsByUsuarioIdAndTreinamentoId(idUsuario, idTreinamento)) {
            throw new ModelNotFoundException("Usuário e treinamento", List.of(idUsuario, idTreinamento));
        }

        UsuarioTreinamento usuarioTreinamento = repo.findByUsuarioIdAndTreinamentoId(idUsuario, idTreinamento);

        if (status == StatusTreinamento.CONCLUIDO) {
            usuarioTreinamento.setTerminadoEm(OffsetDateTime.now());
        }

        usuarioTreinamento.setStatus(status);
        UsuarioTreinamento salvo = repo.save(usuarioTreinamento);
        return mapper.toResponse(salvo);
    }

    public void excluir(Long idTreinamento, Long idUsuario){
        if (!repo.existsByUsuarioIdAndTreinamentoId(idUsuario, idTreinamento)) {
            throw new ModelNotFoundException("Usuário e treinamento", List.of(idUsuario, idTreinamento));
        }

        UsuarioTreinamento usuarioTreinamento = repo.findByUsuarioIdAndTreinamentoId(idUsuario, idTreinamento);
        repo.delete(usuarioTreinamento);
    }
}
