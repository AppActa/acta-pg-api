package br.com.acta.service;

import br.com.acta.common.config.firebase.FirebaseAuthFilter.FirebaseIdentity;
import br.com.acta.common.config.firebase.UsuarioAutenticado;
import br.com.acta.common.handler.exception.FirebaseAccessRevokedException;
import br.com.acta.common.handler.exception.ModelNotFoundException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.dto.core.convite.ConviteMapper;
import br.com.acta.dto.core.convite.ConviteRequestDTO;
import br.com.acta.dto.core.convite.ConviteResponseDTO;
import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.core.Convite;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusConvite;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.repository.padrao.ColaboradorRepository;
import br.com.acta.repository.padrao.ConviteRepository;
import br.com.acta.repository.padrao.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Service
@RequiredArgsConstructor
public class ConviteService {
    private final ConviteRepository repo;
    private final ConviteMapper mapper;
    private final AuthService authService;
    private final EmpresaService empresaService;
    private final UsuarioRepository usuarioRepo;
    private final ColaboradorRepository colaboradorRepo;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Transactional
    public ConviteResponseDTO criar(Long idEmpresa, ConviteRequestDTO dto){
        authService.configurarUsuarioAtual();
        UsuarioAutenticado usuarioAtual = authService.atual();

        if (usuarioRepo.existsByEmailLoginIgnoreCase(dto.email())) throw new UniqueViolationException("E-mail");
        if (colaboradorRepo.existsByCpf(dto.cpf())) throw new UniqueViolationException("CPF");

        Empresa empresa = empresaService.getEntity(idEmpresa);
        Usuario criadoPor = usuarioRepo.findByIdAndEmpresaId(usuarioAtual.idUsuario(), idEmpresa)
                .orElseThrow(() -> new ModelNotFoundException("Usuário", usuarioAtual.idUsuario()));

        Usuario usuario = new Usuario();
        usuario.setEmpresa(empresa);
        usuario.setNome(dto.nome());
        usuario.setEmailLogin(dto.email());
        usuario.setTipo(dto.tipo());
        usuario.setStatus(StatusGeral.PENDENTE);

        Usuario usuarioSalvo = usuarioRepo.save(usuario);

        Colaborador colaborador = new Colaborador();
        colaborador.setEmpresa(empresa);
        colaborador.setUsuario(usuarioSalvo);
        colaborador.setCpf(dto.cpf());
        colaborador.setNome(dto.nome());
        colaborador.setCargo(dto.cargo());
        colaborador.setArea(dto.area());
        colaborador.setDataNascimento(dto.dataNascimento());
        colaborador.setDataContratacao(dto.dataContratacao());
        colaborador.setPermissaoGestor(dto.permissaoGestor());
        colaborador.setStatus(StatusGeral.PENDENTE);
        colaboradorRepo.save(colaborador);

        String token = gerarToken();

        Convite convite = new Convite();
        convite.setUsuarioDestino(usuarioSalvo);
        convite.setEmailDestino(dto.email());
        convite.setTokenHash(gerarHash(token));
        convite.setStatus(StatusConvite.PENDENTE);
        convite.setExpiraEm(OffsetDateTime.now().plusMinutes(30));
        convite.setCriadoPor(criadoPor);

        // TODO service de e-mail para enviar convite

        Convite conviteSalvo = repo.save(convite);
        return mapper.toResponse(conviteSalvo);
    }

    @Transactional
    public Usuario consumirConvite(FirebaseIdentity identity, String tokenConvite){
        String tokenHash = gerarHash(tokenConvite);

        Convite convite = repo.findByTokenHash(tokenHash)
                .orElseThrow(() -> new ModelNotFoundException("Convite"));

        validarConvite(convite, identity);
        convite.setStatus(StatusConvite.USADO);
        convite.setUsadoEm(OffsetDateTime.now());
        repo.save(convite);

        return convite.getUsuarioDestino();
    }

    private void validarConvite(Convite convite, FirebaseIdentity identity){
        if (convite.getStatus() != StatusConvite.PENDENTE) throw new FirebaseAccessRevokedException();
        if (!convite.getEmailDestino().equalsIgnoreCase(identity.email())) throw new FirebaseAccessRevokedException();

        Usuario usuario = convite.getUsuarioDestino();

        if (usuario.getStatus() != StatusGeral.PENDENTE) throw new FirebaseAccessRevokedException();
        if (usuario.getFirebaseUid() != null) throw new FirebaseAccessRevokedException();
        if (usuario.getEmpresa().getStatus() != StatusGeral.ATIVO) throw new FirebaseAccessRevokedException();
        if (usuario.getColaborador() == null || usuario.getColaborador().getStatus() != StatusGeral.PENDENTE) throw new FirebaseAccessRevokedException();
    }

    private String gerarToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String gerarHash(String token){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException nsae){
            throw new IllegalStateException("SHA-256 de hash do convite está indisponível");
        }
    }
}
