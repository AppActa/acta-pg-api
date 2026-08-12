package br.com.acta.service;

import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import br.com.acta.dto.mapper.core.UsuarioMapper;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.repository.padrao.MetaRepository;
import br.com.acta.repository.padrao.TarefaRepository;
import br.com.acta.repository.padrao.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    private UsuarioRepository repo;
    private EmpresaService empresaService;
    private UsuarioService service;

    @BeforeEach
    void setUp() {
        repo = mock(UsuarioRepository.class);
        empresaService = mock(EmpresaService.class);

        service = new UsuarioService(
                empresaService,
                repo,
                mock(UsuarioMapper.class),
                mock(TarefaRepository.class),
                mock(MetaRepository.class)
        );
    }

    @Test
    void antesInserir_deveLancarUniqueViolationException_quandoEmailJaExistir() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
                "Davi Aliaga",
                "Davi@Acta.com",
                "Senha123!",
                TipoUsuario.COLABORADOR,
                1L
        );

        when(repo.existsByEmailLoginIgnoreCase(dto.email()))
                .thenReturn(true);

        UniqueViolationException exception = assertThrows(
                UniqueViolationException.class,
                () -> service.antesInserir(new Usuario(), dto)
        );

        assertEquals(
                "Já existe um registro para E-mail",
                exception.getMessage()
        );

        verify(repo)
                .existsByEmailLoginIgnoreCase(dto.email());

        verifyNoInteractions(empresaService);
    }

    @Test
    void antesInserir_devePararFluxoAntesDeBuscarEmpresa_quandoEmailForDuplicado() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO(
                "Davi Aliaga",
                "usuario@acta.com",
                "Senha123!",
                TipoUsuario.COLABORADOR,
                1L
        );

        when(repo.existsByEmailLoginIgnoreCase(dto.email()))
                .thenReturn(true);

        assertThrows(
                UniqueViolationException.class,
                () -> service.antesInserir(new Usuario(), dto)
        );

        verify(repo)
                .existsByEmailLoginIgnoreCase(dto.email());

        verify(
                empresaService,
                never()
        ).getEntity(anyLong());
    }
}