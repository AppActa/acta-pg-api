package br.com.acta.service;

import br.com.acta.common.handler.exception.*;
import br.com.acta.common.utils.Validador;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloRequestDTO;
import br.com.acta.dto.join.usuario_ciclo.UsuarioCicloResponseDTO;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.PapelCiclo;
import br.com.acta.entity.join.UsuarioCiclo;
import br.com.acta.entity.join.id.UsuarioCicloId;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.dto.mapper.join.UsuarioCicloMapper;
import br.com.acta.repository.composto.UsuarioCicloRepository;
import br.com.acta.repository.padrao.CicloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioCicloService {
    private final CicloService cicloService;
    private final CicloRepository cicloRepo;
    protected final UsuarioService usuarioService;
    private final UsuarioCicloMapper mapper;
    private final UsuarioCicloRepository repo;

    protected UsuarioCiclo getEntity(Long idUsuario, Long idCiclo){
        Ciclo ciclo = cicloService.getEntity(idCiclo);
        Usuario usuario = usuarioService.getEntity(idUsuario);

        Validador.validarMesmoCiclo(ciclo, usuario.getCiclos());
        Validador.validarMesmaEmpresa(ciclo.getEmpresa(), usuario.getEmpresa());
        UsuarioCicloId id = new UsuarioCicloId(idUsuario, idCiclo);

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

        Validador.validarMesmaEmpresa(ciclo.getEmpresa(), usuario.getEmpresa());

        if (repo.existsByUsuarioIdAndCicloId(dto.idUsuario(), idCiclo)) {
            throw new UniqueViolationException("Usuário", "Ciclo");
        }

        UsuarioCiclo usuarioCiclo = mapper.toEntity(dto);
        usuarioCiclo.setCiclo(ciclo);
        usuarioCiclo.setUsuario(usuario);

        // proteção contra race condition
        try {
            UsuarioCiclo salvo = repo.saveAndFlush(usuarioCiclo);
            return mapper.toResponse(salvo);
        } catch (DataIntegrityViolationException dive){
            throw new UniqueViolationException("Usuário", "Ciclo");
        }
    }

    public UsuarioCicloResponseDTO patch(Long idUsuario, Long idCiclo, PapelCiclo papelCiclo){
        UsuarioCiclo usuarioCiclo = getEntity(idUsuario, idCiclo);
        List<UsuarioCiclo> responsaveis = repo.findByCicloIdAndPapelCiclo(idCiclo, PapelCiclo.RESPONSAVEL);

        // se só tiver um responsável e quiser mudar o cargo dele, não deixar
        if (responsaveis.size() == 1 && responsaveis.get(0).getId().getIdUsuario().equals(idUsuario) && !papelCiclo.equals(PapelCiclo.RESPONSAVEL)) {
            throw new PrerequisiteNotMetException("Não é possível mudar o cargo do único responsável do ciclo, substitua o responsável e tente novamente");
        }

        usuarioCiclo.setPapelCiclo(papelCiclo);
        UsuarioCiclo salvo = repo.save(usuarioCiclo);
        return mapper.toResponse(salvo);
    }

    public List<UsuarioCicloResponseDTO> substituirResponsavel(Long idCiclo, Long idUsuarioAntigo, Long idUsuarioNovo){
        Validador.validarMesmoId(idUsuarioAntigo, idUsuarioNovo, false);

        UsuarioCiclo gestorAntigo = getEntity(idUsuarioAntigo, idCiclo);
        UsuarioCiclo gestorNovo = getEntity(idUsuarioNovo, idCiclo);
        Ciclo ciclo = cicloService.getEntity(idCiclo);

        Validador.validarMesmaEmpresa(ciclo.getEmpresa(), gestorAntigo.getUsuario().getEmpresa());
        Validador.validarMesmaEmpresa(ciclo.getEmpresa(), gestorNovo.getUsuario().getEmpresa());
        Validador.validarMesmoCiclo(ciclo, Set.of(gestorAntigo, gestorNovo));

        if (!gestorAntigo.getPapelCiclo().equals(PapelCiclo.RESPONSAVEL)) {
            throw new InvalidRequestException("O usuário antigo não é o responsável do ciclo");
        }

        gestorNovo.setPapelCiclo(PapelCiclo.RESPONSAVEL);
        gestorAntigo.setPapelCiclo(PapelCiclo.OBSERVADOR);
        ciclo.setGestor(gestorNovo.getUsuario());

        cicloRepo.save(ciclo);
        List<UsuarioCiclo> salvo = repo.saveAll(List.of(gestorNovo, gestorAntigo));
        return mapper.toResponseList(salvo);
    }

    public void excluir(Long idUsuario, Long idCiclo){
        UsuarioCiclo usuarioCiclo = getEntity(idUsuario, idCiclo);

        if (usuarioCiclo.getPapelCiclo().equals(PapelCiclo.RESPONSAVEL)) {
            throw new PrerequisiteNotMetException("remover o usuário do ciclo", "não é possível remover o responsável");
        }

        repo.delete(usuarioCiclo);
    }
}