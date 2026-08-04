package br.com.acta.common.utils;

import br.com.acta.common.handler.exception.UniqueViolationException;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.entity.join.UsuarioCiclo;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.common.handler.exception.BusinessRuleException;
import br.com.acta.common.handler.exception.ImmutableFieldException;
import br.com.acta.common.handler.exception.InexistentFieldException;

import java.util.Map;
import java.util.Objects;
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

    public static void validarCicloAberto(Ciclo ciclo) {
        if (ciclo.getStatus() == StatusCiclo.CONCLUIDO || ciclo.getStatus() == StatusCiclo.CANCELADO) {
            throw new BusinessRuleException("Não é possível realizar esta operação em um ciclo que não está aberto");
        }
    }

    public static void validarMesmoCiclo(Ciclo a, Ciclo b) {
        if (!a.equals(b)) {
            throw new BusinessRuleException("As entidades devem pertencer ao mesmo ciclo");
        }

        validarCicloAberto(a);
    }

    public static void validarMesmoCiclo(Ciclo a, Set<UsuarioCiclo> ciclos) {
        validarCicloAberto(a);
        boolean participaCiclo = ciclos.stream()
                .map(UsuarioCiclo::getCiclo)
                .anyMatch(ciclo -> Objects.equals(ciclo.getId(), a.getId()));

        if (!participaCiclo) {
            throw new BusinessRuleException("O usuário não participa deste ciclo");
        }
    }

    public static void validarMesmoId(Long id1, Long id2, boolean deveSerIgual) {
        if (deveSerIgual && !id1.equals(id2)) {
            throw new BusinessRuleException("Os IDs das entidades devem ser iguais");
        }

        if (!deveSerIgual && id1.equals(id2)) {
            throw new BusinessRuleException("Os IDs das entidades não devem ser iguais");
        }
    }

    public static void validarUnico(boolean jaExiste, String campo){
        if (jaExiste) {
            throw new UniqueViolationException(campo);
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
