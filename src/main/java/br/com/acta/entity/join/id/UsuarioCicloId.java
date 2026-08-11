package br.com.acta.entity.join.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
public class UsuarioCicloId implements Serializable {
    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_ciclo", nullable = false)
    private Long idCiclo;
}
