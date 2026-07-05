package br.com.acta.entity.join.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PriorizacaoProblemaId implements Serializable {
    @Column(name = "id_problema", nullable = false)
    private Long idProblema;
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;
}
