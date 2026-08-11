package br.com.acta.common.utils;

import br.com.acta.common.handler.exception.*;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.enums.StatusProblema;
import br.com.acta.entity.enums.StatusTarefa;
import br.com.acta.entity.enums.TipoUsuario;
import br.com.acta.entity.join.UsuarioCiclo;
import br.com.acta.entity.pdca.Ciclo;
import br.com.acta.entity.pdca.Problema;
import br.com.acta.entity.pdca.Tarefa;

import java.util.List;
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
        throw new ForbiddenOperationException();
    }

    public static void validarCicloAberto(Ciclo ciclo) {
        if (ciclo.getStatus() == StatusCiclo.CONCLUIDO || ciclo.getStatus() == StatusCiclo.CANCELADO) {
            throw new InvalidResourceStatusException("ciclo", List.of(StatusCiclo.CONCLUIDO.toString(), StatusCiclo.CANCELADO.toString()));
        }
    }

    public static void validarProblemaAberto(Problema problema) {
        if (problema.getStatus() == StatusProblema.RESOLVIDO || problema.getStatus() == StatusProblema.DESCARTADO) {
            throw new InvalidResourceStatusException("problema", List.of(StatusProblema.RESOLVIDO.toString(), StatusProblema.DESCARTADO.toString()));
        }

        validarCicloAberto(problema.getCiclo());
    }

    public static void validarTarefaAberta(Tarefa tarefa){
        if (tarefa.getStatus() == StatusTarefa.CONCLUIDA || tarefa.getStatus() == StatusTarefa.CANCELADA) {
            throw new InvalidResourceStatusException("tarefa", List.of(StatusTarefa.CONCLUIDA.toString(), StatusTarefa.CANCELADA.toString()));
        }

        validarCicloAberto(tarefa.getPlanoAcao().getCiclo());
    }

    public static void validarMesmaEmpresa(Empresa a, Empresa b){
        if (!a.equals(b)) {
            throw new InvalidRelationshipException("pertencer à mesma empresa");
        }
    }

    public static void validarMesmoCiclo(Ciclo a, Ciclo b) {
        if (!a.equals(b)) {
            throw new InvalidRelationshipException("pertencer ao mesmo ciclo");
        }

        validarCicloAberto(a);
    }

    public static void validarMesmoCiclo(Ciclo a, Set<UsuarioCiclo> ciclos) {
        validarCicloAberto(a);
        boolean participaCiclo = ciclos.stream()
                .map(UsuarioCiclo::getCiclo)
                .anyMatch(ciclo -> Objects.equals(ciclo.getId(), a.getId()));

        if (!participaCiclo) {
            throw new InvalidRelationshipException("participar do mesmo ciclo");
        }
    }

    public static void validarMesmoId(Long id1, Long id2, boolean deveSerIgual) {
        if (deveSerIgual && !id1.equals(id2)) {
            throw new InvalidRelationshipException("ter os IDs iguais");
        }

        if (!deveSerIgual && id1.equals(id2)) {
            throw new InvalidRelationshipException("ter os IDs diferentes");
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
