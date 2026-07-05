package br.com.acta.entity.join;

import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusTreinamento;
import br.com.acta.entity.join.id.UsuarioTreinamentoId;
import br.com.acta.entity.pdca.Treinamento;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "usuario_treinamento", schema = "pdca", uniqueConstraints = @UniqueConstraint(name = "usuario_treinamento_unique_0", columnNames = {"id_usuario", "id_treinamento"}))
@Getter
@Setter
@NoArgsConstructor
public class UsuarioTreinamento{
    @EmbeddedId
    private UsuarioTreinamentoId id;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @MapsId("idTreinamento")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_treinamento", nullable = false, updatable = false)
    private Treinamento treinamento;

    @Column(name = "obrigatorio", nullable = false)
    private Boolean obrigatorio;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTreinamento status;

    @Column(name = "terminado_em", updatable = false)
    private OffsetDateTime confirmadoEm;
}
