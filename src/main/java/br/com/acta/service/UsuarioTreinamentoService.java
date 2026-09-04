package br.com.acta.service;

import br.com.acta.common.handler.exception.*;
import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoRequestDTO;
import br.com.acta.dto.join.usuario_treinamento.UsuarioTreinamentoResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusTreinamento;
import br.com.acta.entity.join.UsuarioTreinamento;
import br.com.acta.entity.pdca.Treinamento;
import br.com.acta.dto.mapper.join.UsuarioTreinamentoMapper;
import br.com.acta.repository.composto.UsuarioTreinamentoRepository;
import br.com.acta.common.utils.Validador;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioTreinamentoService {
    private final UsuarioTreinamentoRepository repo;
    private final UsuarioTreinamentoMapper mapper;
    private final TreinamentoService treinamentoService;
    private final UsuarioService usuarioService;

    @PreAuthorize("isAuthenticated()")
    @Transactional(readOnly = true)
    public List<UsuarioTreinamentoResponseDTO> buscar(Long idTreinamento){
        List<UsuarioTreinamento> usuarios = repo.findByTreinamentoId(idTreinamento);

        return mapper.toResponseList(usuarios);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public UsuarioTreinamentoResponseDTO inserir(Long idTreinamento, UsuarioTreinamentoRequestDTO dto){
        Treinamento treinamento = treinamentoService.getEntity(idTreinamento);
        Usuario usuario = usuarioService.getEntity(dto.idUsuario());

        Validador.validarMesmoCiclo(treinamento.getCiclo(), usuario.getCiclos());
        if (repo.existsByUsuarioIdAndTreinamentoId(usuario.getId(), treinamento.getId())) {
            throw new UniqueViolationException("Usuário", "Treinamento");
        }

        UsuarioTreinamento usuarioTreinamento = mapper.toEntity(dto);
        usuarioTreinamento.setTreinamento(treinamento);
        usuarioTreinamento.setUsuario(usuario);
        usuarioTreinamento.setStatus(StatusTreinamento.PENDENTE);

        // proteção contra race condition
        try {
            UsuarioTreinamento salvo = repo.saveAndFlush(usuarioTreinamento);
            return mapper.toResponse(salvo);
        } catch (DataIntegrityViolationException dive){
            throw new UniqueViolationException("Usuário", "Treinamento");
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR') or authService.isProprioUsuario(#idUsuario)")
    @Transactional
    public UsuarioTreinamentoResponseDTO patchStatus(Long idTreinamento, Long idUsuario, StatusTreinamento status){
        if (!repo.existsByUsuarioIdAndTreinamentoId(idUsuario, idTreinamento)) {
            throw new ModelNotFoundException("Usuário e treinamento", List.of(idUsuario, idTreinamento));
        }

        UsuarioTreinamento usuarioTreinamento = repo.findByUsuarioIdAndTreinamentoId(idUsuario, idTreinamento);

        if (!usuarioTreinamento.getStatus().podeAtualizarStatus(status)) {
            throw new StatusUpdateException(usuarioTreinamento.getStatus().toString(), status.toString());
        }

        if (status == StatusTreinamento.CONCLUIDO) {
            usuarioTreinamento.setTerminadoEm(OffsetDateTime.now());
        }

        usuarioTreinamento.setStatus(status);
        UsuarioTreinamento salvo = repo.save(usuarioTreinamento);
        return mapper.toResponse(salvo);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'GESTOR')")
    @Transactional
    public void excluir(Long idTreinamento, Long idUsuario){
        if (!repo.existsByUsuarioIdAndTreinamentoId(idUsuario, idTreinamento)) {
            throw new ModelNotFoundException("Usuário e treinamento", List.of(idUsuario, idTreinamento));
        }

        UsuarioTreinamento usuarioTreinamento = repo.findByUsuarioIdAndTreinamentoId(idUsuario, idTreinamento);

        if (usuarioTreinamento.getStatus() == StatusTreinamento.CONCLUIDO) {
            throw new InvalidResourceStatusException("excluir", "Treinamento", StatusTreinamento.CONCLUIDO.toString());
        }

        repo.delete(usuarioTreinamento);
    }
}
