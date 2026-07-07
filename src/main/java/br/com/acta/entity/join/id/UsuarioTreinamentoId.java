package br.com.acta.entity.join.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class UsuarioTreinamentoId implements Serializable {
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_treinamento", nullable = false)
    private Long idTreinamento;
}
