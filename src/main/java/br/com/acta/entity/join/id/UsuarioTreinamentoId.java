package br.com.acta.entity.join.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class UsuarioTreinamentoId {
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_treinamento", nullable = false)
    private Long idTreinamento;
}
