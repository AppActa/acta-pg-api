package br.com.acta.service;

import br.com.acta.common.client.BrevoClient;
import br.com.acta.common.config.firebase.FirebaseAuthFilter.FirebaseIdentity;
import br.com.acta.common.handler.exception.BusinessRuleException;
import br.com.acta.common.handler.exception.FirebaseAccessRevokedException;
import br.com.acta.common.handler.exception.RegexException;
import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.common.utils.TokenUtils;
import br.com.acta.dto.core.colaborador.ColaboradorMapper;
import br.com.acta.dto.core.colaborador.ColaboradorRequestDTO;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.empresa.EmpresaMapper;
import br.com.acta.dto.core.empresa.EmpresaRequestDTO;
import br.com.acta.dto.core.empresa.OnboardingInicioRequestDTO;
import br.com.acta.dto.core.empresa.OnboardingMapper;
import br.com.acta.dto.core.empresa.OnboardingRequestDTO;
import br.com.acta.dto.core.empresa.OnboardingResponseDTO;
import br.com.acta.dto.core.empresa.endereco.EnderecoRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioMapper;
import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.core.Convite;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusGeral;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.repository.padrao.ColaboradorRepository;
import br.com.acta.repository.padrao.ConviteRepository;
import br.com.acta.repository.padrao.EmpresaRepository;
import br.com.acta.repository.padrao.UsuarioRepository;
import br.com.caelum.stella.validation.CNPJValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OnboardingService {
    private static final int VALIDADE_CONVITE_MINUTOS = 30;

    private final EmpresaRepository empresaRepo;
    private final UsuarioRepository usuarioRepo;
    private final ColaboradorRepository colaboradorRepo;
    private final ConviteRepository conviteRepo;
    private final EmpresaMapper empresaMapper;
    private final ColaboradorMapper colaboradorMapper;
    private final UsuarioMapper usuarioMapper;
    private final OnboardingMapper onboardingMapper;
    private final BrevoClient brevoClient;
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    @PreAuthorize("hasAuthority('ROLE_FIREBASE')")
    @Transactional
    public OnboardingResponseDTO iniciar(FirebaseIdentity identity, OnboardingInicioRequestDTO dto) {
        validarIdentidade(identity, dto.gestor().email());

        if (usuarioRepo.existsByEmailLoginIgnoreCase(identity.email())) throw new UniqueViolationException("E-mail");
        if (colaboradorRepo.existsByCpf(dto.gestor().cpf())) throw new UniqueViolationException("CPF");
        if (!cnpjValidator.isEligible(dto.cnpj())) throw new RegexException("CNPJ");

        Empresa empresa = empresaRepo.findByCnpj(dto.cnpj()).orElse(null);
        if (empresa == null) return onboardingMapper.solicitarDadosEmpresa();

        Usuario criadoPor = usuarioRepo.findByTipoAndStatusAndEmpresaId(TipoUsuario.ADMIN, StatusGeral.ATIVO, empresa.getId())
            .stream()
            .findFirst()
            .orElseThrow(() -> new BusinessRuleException("A empresa não possui um administrador ativo"));

        Usuario usuario = criarUsuarioPendente(dto.gestor(), identity.email(), empresa);
        Colaborador colaborador = criarColaboradorPendente(dto.gestor(), identity.email(), empresa, usuario);
        usuario.setColaborador(colaborador);

        String codigo = TokenUtils.gerarCodigo();
        Convite convite = new Convite();
        convite.setUsuario(usuario);
        convite.setEmailDestino(usuario.getEmailLogin());
        convite.setTokenHash(TokenUtils.hashToken(codigo));
        convite.setStatus("PENDENTE");
        convite.setExpiraEm(OffsetDateTime.now().plusMinutes(VALIDADE_CONVITE_MINUTOS));
        convite.setCriadoPor(criadoPor);
        Convite conviteSalvo = conviteRepo.save(convite);

        conviteRepo.flush();
        brevoClient.enviarConviteAdmin(conviteSalvo.getEmailDestino(), usuario.getNome(), empresa.getNome(), conviteSalvo.getExpiraEm(), codigo);

        return onboardingMapper.toResponse(usuario, true, true, false);
    }

    @PreAuthorize("hasAuthority('ROLE_FIREBASE')")
    @Transactional
    public OnboardingResponseDTO cadastrarEmpresa(FirebaseIdentity identity, OnboardingRequestDTO dto) {
        validarIdentidade(identity, dto.gestor().email());

        if (!cnpjValidator.isEligible(dto.empresa().cnpj())) throw new RegexException("CNPJ");
        if (empresaRepo.existsByCnpj(dto.empresa().cnpj())) return iniciar(identity, new OnboardingInicioRequestDTO(dto.gestor(), dto.empresa().cnpj()));
        if (usuarioRepo.existsByEmailLoginIgnoreCase(identity.email())) throw new UniqueViolationException("E-mail");
        if (colaboradorRepo.existsByCpf(dto.gestor().cpf())) throw new UniqueViolationException("CPF");

        EmpresaRequestDTO empresaRequest = new EmpresaRequestDTO(
                dto.empresa().cnpj(),
                dto.empresa().nome(),
                dto.empresa().tamanhoEmpresa(),
                dto.empresa().setorEmpresa(),
                List.of(new TelefoneRequestDTO(dto.empresa().telefoneEmpresa(), true)),
                List.of(new EmailRequestDTO(dto.empresa().emailEmpresa(), true)),
                List.of(new EnderecoRequestDTO(
                        dto.endereco().cep(),
                        dto.endereco().uf(),
                        dto.endereco().cidade(),
                        dto.endereco().bairro(),
                        dto.endereco().logradouro(),
                        dto.endereco().numeroEndereco(),
                        dto.endereco().complemento(),
                        true)));

        Empresa empresa = empresaMapper.toEntity(empresaRequest);
        empresa.setStatus(StatusGeral.PENDENTE);
        Empresa empresaSalva = empresaRepo.save(empresa);

        Usuario usuario = criarUsuarioPendente(dto.gestor(), identity.email(), empresaSalva, identity.firebaseUid());
        Colaborador colaborador = criarColaboradorPendente(dto.gestor(), identity.email(), empresaSalva, usuario);
        usuario.setColaborador(colaborador);

        return onboardingMapper.toResponse(usuario, false, false, false);
    }

    private void validarIdentidade(FirebaseIdentity identity, String emailGestor) {
        if (identity == null || !identity.emailVerificado() || identity.email() == null || !identity.email().equalsIgnoreCase(emailGestor))
            throw new FirebaseAccessRevokedException();
    }

    private Usuario criarUsuarioPendente(OnboardingRequestDTO.Gestor gestor, String email, Empresa empresa) {
        return criarUsuarioPendente(gestor, email, empresa, null);
    }

    private Usuario criarUsuarioPendente(OnboardingRequestDTO.Gestor gestor, String email, Empresa empresa, String firebaseUid) {
        UsuarioRequestDTO request = new UsuarioRequestDTO(gestor.nome(), email.trim(), firebaseUid, TipoUsuario.ADMIN);
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setStatus(StatusGeral.PENDENTE);
        usuario.setEmpresa(empresa);
        return usuarioRepo.save(usuario);
    }

    private Colaborador criarColaboradorPendente(OnboardingRequestDTO.Gestor gestor, String email, Empresa empresa, Usuario usuario) {
        UsuarioRequestDTO usuarioRequest = new UsuarioRequestDTO(gestor.nome(), email, usuario.getFirebaseUid(), TipoUsuario.ADMIN);
        ColaboradorRequestDTO request = new ColaboradorRequestDTO(
                gestor.cpf(),
                gestor.nome(),
                gestor.nome(),
                gestor.cargo(),
                gestor.area(),
                gestor.dataNascimento(),
                gestor.dataContratacao(),
                true,
                List.of(new EmailRequestDTO(email, true)),
                List.of(),
                usuarioRequest);

        Colaborador colaborador = colaboradorMapper.toEntity(request);
        colaborador.setStatus(StatusGeral.PENDENTE);
        colaborador.setEmpresa(empresa);
        colaborador.setUsuario(usuario);
        return colaboradorRepo.save(colaborador);
    }
}