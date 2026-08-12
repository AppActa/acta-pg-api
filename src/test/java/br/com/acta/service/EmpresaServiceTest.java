package br.com.acta.service;

import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneResponseDTO;
import br.com.acta.dto.mapper.core.EmpresaMapper;
import br.com.acta.dto.mapper.core.EnderecoMapper;
import br.com.acta.dto.mapper.core.contato.EmailEmpresaMapper;
import br.com.acta.dto.mapper.core.contato.TelefoneEmpresaMapper;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.contato.EmailEmpresa;
import br.com.acta.entity.core.contato.TelefoneEmpresa;
import br.com.acta.repository.padrao.EmailEmpresaRepository;
import br.com.acta.repository.padrao.EmpresaRepository;
import br.com.acta.repository.padrao.EnderecoRepository;
import br.com.acta.repository.padrao.TelefoneEmpresaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EmpresaServiceTest {

    private EmpresaRepository repo;

    private EmailEmpresaRepository emailRepo;
    private TelefoneEmpresaRepository telefoneRepo;

    private EmailEmpresaMapper emailMapper;
    private TelefoneEmpresaMapper telefoneMapper;

    private EmpresaService service;

    @BeforeEach
    void setUp() {
        repo = mock(EmpresaRepository.class);

        emailRepo = mock(EmailEmpresaRepository.class);
        telefoneRepo = mock(TelefoneEmpresaRepository.class);

        emailMapper = mock(EmailEmpresaMapper.class);
        telefoneMapper = mock(TelefoneEmpresaMapper.class);

        service = new EmpresaService(
                repo,
                mock(EmpresaMapper.class),
                emailMapper,
                emailRepo,
                telefoneMapper,
                telefoneRepo,
                mock(EnderecoMapper.class),
                mock(EnderecoRepository.class)
        );
    }

    @Test
    void inserirEmail_deveLancarUniqueViolationException_quandoEmailJaExistir() {
        Empresa empresa = new Empresa();

        EmailRequestDTO dto = new EmailRequestDTO(
                "contato@acta.com",
                true
        );

        when(repo.findById(1L))
                .thenReturn(Optional.of(empresa));

        when(emailRepo.existsByContatoIgnoreCase(dto.email()))
                .thenReturn(true);

        UniqueViolationException exception = assertThrows(
                UniqueViolationException.class,
                () -> service.inserirEmail(1L, dto)
        );

        assertEquals(
                "Já existe um registro para E-mail",
                exception.getMessage()
        );

        verify(emailRepo)
                .existsByContatoIgnoreCase(dto.email());

        verify(emailMapper, never())
                .toEntity(any());

        verify(emailRepo, never())
                .save(any());
    }

    @Test
    void inserirEmail_deveSalvarEmail_quandoEmailNaoExistir() {
        Empresa empresa = new Empresa();

        EmailRequestDTO dto = new EmailRequestDTO(
                "contato@acta.com",
                true
        );

        EmailEmpresa email = new EmailEmpresa();

        EmailResponseDTO response = new EmailResponseDTO(
                1L,
                dto.email(),
                true,
                null
        );

        when(repo.findById(1L))
                .thenReturn(Optional.of(empresa));

        when(emailRepo.existsByContatoIgnoreCase(dto.email()))
                .thenReturn(false);

        when(emailMapper.toEntity(dto))
                .thenReturn(email);

        when(emailRepo.save(email))
                .thenReturn(email);

        when(emailMapper.toResponse(email))
                .thenReturn(response);

        EmailResponseDTO resultado =
                service.inserirEmail(1L, dto);

        assertSame(response, resultado);
        assertSame(empresa, email.getEmpresa());

        verify(emailRepo)
                .existsByContatoIgnoreCase(dto.email());

        verify(emailRepo)
                .save(email);
    }

    @Test
    void inserirTelefone_deveLancarUniqueViolationException_quandoTelefoneJaExistir() {
        Empresa empresa = new Empresa();

        TelefoneRequestDTO dto = new TelefoneRequestDTO(
                "11999999999",
                true
        );

        when(repo.findById(1L))
                .thenReturn(Optional.of(empresa));

        when(telefoneRepo.existsByContatoIgnoreCase(dto.numero()))
                .thenReturn(true);

        UniqueViolationException exception = assertThrows(
                UniqueViolationException.class,
                () -> service.inserirTelefone(1L, dto)
        );

        assertEquals(
                "Já existe um registro para Telefone",
                exception.getMessage()
        );

        verify(telefoneRepo)
                .existsByContatoIgnoreCase(dto.numero());

        verify(telefoneRepo, never())
                .save(any());
    }

    @Test
    void inserirTelefone_deveSalvarTelefone_quandoTelefoneNaoExistir() {
        Empresa empresa = new Empresa();

        TelefoneRequestDTO dto = new TelefoneRequestDTO(
                "11999999999",
                true
        );

        TelefoneEmpresa telefone = new TelefoneEmpresa();

        TelefoneResponseDTO response = new TelefoneResponseDTO(
                1L,
                dto.numero(),
                true,
                null
        );

        when(repo.findById(1L))
                .thenReturn(Optional.of(empresa));

        when(telefoneRepo.existsByContatoIgnoreCase(dto.numero()))
                .thenReturn(false);

        when(telefoneMapper.toEntity(dto))
                .thenReturn(telefone);

        when(telefoneRepo.save(telefone))
                .thenReturn(telefone);

        when(telefoneMapper.toResponse(telefone))
                .thenReturn(response);

        TelefoneResponseDTO resultado =
                service.inserirTelefone(1L, dto);

        assertSame(response, resultado);
        assertSame(empresa, telefone.getEmpresa());

        verify(telefoneRepo)
                .existsByContatoIgnoreCase(dto.numero());

        verify(telefoneRepo)
                .save(telefone);
    }
}