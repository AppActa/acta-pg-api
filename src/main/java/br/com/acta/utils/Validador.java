package br.com.acta.utils;

import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.entity.join.UsuarioCiclo;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.handler.exception.BusinessRuleException;
import br.com.acta.handler.exception.ImmutableFieldException;
import br.com.acta.handler.exception.InexistentFieldException;
import java.util.Map;
import java.util.Set;

public final class Validador {
    public static void validarTipoUsuario(Usuario usuario, TipoUsuario... permitidos){
        for (TipoUsuario tipo : permitidos) {
            if (usuario.getTipo() == tipo) {
                return;
            }
        }
        throw new BusinessRuleException("Usuário não tem permissão para realizar esta operação");
    }

    // todo
    /*
    public static void validarSubproblemas(Problema problema, Problema problemaPai){
        validarMesmoCiclo(problema.getCiclo(), problemaPai.getCiclo());
    }*/

    public static void validarMesmoCiclo(Ciclo a, Ciclo b) {
        if (!a.equals(b)) {
            throw new BusinessRuleException("As entidades devem pertencer ao mesmo ciclo");
        }
    }

    public static void validarMesmoCiclo(Ciclo a, Set<UsuarioCiclo> ciclos) {
        ciclos.forEach(ciclo -> validarMesmoCiclo(a, ciclo.getCiclo()));
    }

    public static void validarMesmoId(Long id1, Long id2, boolean deveSerIgual) {
        if (deveSerIgual && !id1.equals(id2)) {
            throw new BusinessRuleException("Os IDs das entidades devem ser iguais");
        }

        if (!deveSerIgual && id1.equals(id2)) {
            throw new BusinessRuleException("Os IDs das entidades não devem ser iguais");
        }
    }

    public static void validarCampos(Map<String, Object> campos, PatchConfig patchConfig){
        for (String campo : campos.keySet()) {
            if (!patchConfig.allCampos().contains(campo)){
                throw new InexistentFieldException(campo);
            }

            if (!patchConfig.patchableCampos().contains(campo)){
                throw new ImmutableFieldException(campo);
            }
        }
    }
}
