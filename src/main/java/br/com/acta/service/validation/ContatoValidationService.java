package br.com.acta.service.validation;

import br.com.acta.common.utils.Validador;
import br.com.acta.repository.padrao.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContatoValidationService {
    private final UsuarioRepository usuarioRepo;
    private final EmailEmpresaRepository emailEmpresaRepo;
    private final EmailColaboradorRepository emailColaboradorRepo;
    private final TelefoneColaboradorRepository telefoneColaboradorRepo;
    private final TelefoneEmpresaRepository telefoneEmpresaRepo;

    public void validarEmailUnico(String email){
        boolean existeEmpresa = emailEmpresaRepo.existsByContatoIgnoreCase(email);
        boolean existeColaborador = emailColaboradorRepo.existsByContatoIgnoreCase(email);
        boolean existeUsuario = usuarioRepo.existsByEmailLoginIgnoreCase(email);
        boolean existe = existeEmpresa || existeColaborador || existeUsuario;

        Validador.validarUnico(existe, "email");
    }

    public void validarTelefoneUnico(String telefone){
        boolean existeEmpresa = emailEmpresaRepo.existsByContatoIgnoreCase(telefone);
        boolean existeColaborador = telefoneColaboradorRepo.existsByContatoIgnoreCase(telefone);
        boolean existeUsuario = usuarioRepo.existsByEmailLoginIgnoreCase(telefone);
        boolean existe = existeEmpresa || existeColaborador || existeUsuario;

        Validador.validarUnico(existe, "telefone");

    }
}
