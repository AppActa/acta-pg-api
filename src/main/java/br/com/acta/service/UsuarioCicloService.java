package br.com.acta.service;

import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloRequestDTO;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.PapelCiclo;
import br.com.acta.entity.join.UsuarioCiclo;
import br.com.acta.entity.join.id.UsuarioCicloId;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.handler.exception.ModelNotFoundException;
import br.com.acta.handler.exception.UniqueViolationException;
import br.com.acta.mapper.join.UsuarioCicloMapper;
import br.com.acta.repository.composto.UsuarioCicloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioCicloService {
    private final CicloService cicloService;
    private final UsuarioService usuarioService;
    private final UsuarioCicloMapper mapper;
    private final UsuarioCicloRepository repo;

    protected UsuarioCiclo getEntity(UsuarioCicloId id){
        return repo.findById(id).orElseThrow(() -> new ModelNotFoundException("UsuarioCiclo", List.of(id.getIdUsuario(), id.getIdCiclo())));
    }

    public List<UsuarioCicloResponseDTO> buscar(Long idCiclo){
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        List<UsuarioCiclo> usuarios = ciclo.getColaboradores().stream().toList();

        return mapper.toResponseList(usuarios);
    }

    public UsuarioCicloResponseDTO inserir(UsuarioCicloRequestDTO dto, Long idCiclo){
        Usuario usuario = usuarioService.getEntity(dto.idUsuario());
        Ciclo ciclo = cicloService.getEntity(idCiclo);

        if (repo.existsByUsuarioIdAndCicloId(dto.idUsuario(), idCiclo)) {
            throw new UniqueViolationException("Usuário", "Ciclo");
        }

        UsuarioCiclo usuarioCiclo = mapper.toEntity(dto);
        usuarioCiclo.setCiclo(ciclo);
        usuarioCiclo.setUsuario(usuario);

        UsuarioCiclo salvo = repo.save(usuarioCiclo);
        return mapper.toResponse(salvo);
    }

    public UsuarioCicloResponseDTO patch(Long idUsuario, Long idCiclo, PapelCiclo papelCiclo){
        UsuarioCiclo usuarioCiclo = getEntity(new UsuarioCicloId(idUsuario, idCiclo));
        usuarioCiclo.setPapelCiclo(papelCiclo);

        UsuarioCiclo salvo = repo.save(usuarioCiclo);
        return mapper.toResponse(salvo);
    }

    public List<UsuarioCicloResponseDTO> substituirResponsavel(Long idCiclo, Long idUsuarioAntigo, Long idUsuarioNovo){
        if (idUsuarioAntigo.equals(idUsuarioNovo)) {
            throw new IllegalArgumentException("O usuário antigo e o usuário novo são o mesmo");
        }

        UsuarioCiclo gestorAntigo = getEntity(new UsuarioCicloId(idUsuarioAntigo, idCiclo));
        UsuarioCiclo gestorNovo = getEntity(new UsuarioCicloId(idUsuarioNovo, idCiclo));

        if (!gestorAntigo.getPapelCiclo().equals(PapelCiclo.RESPONSAVEL)) {
            throw new IllegalArgumentException("O usuário antigo não é o responsável do ciclo");
        }

        gestorNovo.setPapelCiclo(PapelCiclo.RESPONSAVEL);
        gestorAntigo.setPapelCiclo(PapelCiclo.OBSERVADOR);

        List<UsuarioCiclo> salvo = repo.saveAll(List.of(gestorNovo, gestorAntigo));
        return mapper.toResponseList(salvo);
    }

    public void excluir(Long idUsuario, Long idCiclo){
        UsuarioCiclo usuarioCiclo = getEntity(new UsuarioCicloId(idUsuario, idCiclo));

        if (usuarioCiclo.getPapelCiclo().equals(PapelCiclo.RESPONSAVEL)) {
            throw new IllegalStateException("N" +
                    "ão é possível remover o responsável do ciclo. Altere o responsável antes de remover este usuário.");
        }

        repo.delete(usuarioCiclo);
    }
}

