package br.com.acta.entity.join;

import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.PapelCiclo;
import br.com.acta.entity.join.id.UsuarioCicloId;
import br.com.acta.entity.pdca.Ciclo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "usuario_ciclo", schema = "pdca",
uniqueConstraints = {@UniqueConstraint(name = "usuario_ciclo_unique_0", columnNames = {"id_usuario", "id_ciclo"})
})
@Getter
@Setter
@NoArgsConstructor
public class UsuarioCiclo {
    @EmbeddedId
    private UsuarioCicloId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @MapsId("idCiclo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciclo", nullable = false, updatable = false)
    private Ciclo ciclo;

    @Column(name = "papel_ciclo", nullable = false)
    @Enumerated(EnumType.STRING)
    private PapelCiclo papelCiclo;
}