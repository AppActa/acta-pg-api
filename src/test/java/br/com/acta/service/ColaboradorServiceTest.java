package br.com.acta.service;

import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.dto.core.colaborador.ColaboradorRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.dto.mapper.core.ColaboradorMapper;
import br.com.acta.dto.mapper.core.contato.EmailColaboradorMapper;
import br.com.acta.dto.mapper.core.contato.TelefoneColaboradorMapper;
import br.com.acta.entity.core.Colaborador;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.repository.padrao.ColaboradorRepository;
import br.com.acta.repository.padrao.EmailColaboradorRepository;
import br.com.acta.repository.padrao.TarefaRepository;
import br.com.acta.repository.padrao.TelefoneColaboradorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ColaboradorServiceTest {

    private ColaboradorRepository repo;
    private ColaboradorService service;

    @BeforeEach
    void setUp() {
        repo = mock(ColaboradorRepository.class);

        service = new ColaboradorService(
                repo,
                mock(ColaboradorMapper.class),
                mock(EmpresaService.class),
                mock(UsuarioService.class),
                mock(TarefaRepository.class),
                mock(EmailColaboradorRepository.class),
                mock(TelefoneColaboradorRepository.class),
                mock(EmailColaboradorMapper.class),
                mock(TelefoneColaboradorMapper.class)
        );
    }

    @Test
    void antesInserir_deveLancarUniqueViolationException_quandoCpfJaExistir() {
        ColaboradorRequestDTO dto = criarDto("52998224725");

        when(repo.existsByCpf(dto.cpf()))
                .thenReturn(true);

        UniqueViolationException exception = assertThrows(
                UniqueViolationException.class,
                () -> service.antesInserir(new Colaborador(), dto)
        );

        assertEquals(
                "Já existe um registro para CPF",
                exception.getMessage()
        );

        verify(repo).existsByCpf(dto.cpf());
    }

    @Test
    void antesInserir_naoDeveLancarExcecao_quandoCpfNaoExistir() {
        ColaboradorRequestDTO dto = criarDto("52998224725");

        when(repo.existsByCpf(dto.cpf()))
                .thenReturn(false);

        assertDoesNotThrow(
                () -> service.antesInserir(new Colaborador(), dto)
        );

        verify(repo).existsByCpf(dto.cpf());
    }

    private ColaboradorRequestDTO criarDto(String cpf) {
        UsuarioRequestDTO usuario = new UsuarioRequestDTO(
                "Davi Aliaga",
                "davi@acta.com",
                "Senha123!",
                TipoUsuario.COLABORADOR,
                1L
        );

        return new ColaboradorRequestDTO(
                cpf,
                "Davi Aliaga",
                "Desenvolvedor",
                "Tecnologia",
                LocalDate.of(2008, 1, 1),
                LocalDate.of(2026, 1, 1),
                false,
                List.of(),
                List.of(),
                usuario,
                1L
        );
    }
}