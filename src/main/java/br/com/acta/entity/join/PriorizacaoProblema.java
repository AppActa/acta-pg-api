package br.com.acta.entity.join;

import br.com.acta.entity.base.auditoria.AuditoriaBase;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.join.id.PriorizacaoProblemaId;
import br.com.acta.entity.pdca.Problema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "priorizacao_problema_usuario", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class PriorizacaoProblema {
    @EmbeddedId
    private PriorizacaoProblemaId id;

    @Column(name = "posicao", nullable = false)
    private Integer posicao;

    @Column(name = "peso_calculado", nullable = false, precision = 3, scale = 2)
    private BigDecimal pesoCalculado;

    @MapsId("idProblema")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_problema", nullable = false)
    private Problema problema;

    @MapsId("idUsuario")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    // Atributos vindos de AuditoriaBase e ImutavelBase, porque classe não pode ter um id (vindo de ModelBase)
    @Column(name = "criado_em", nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime criadoEm;

    @Column(name = "atualizado_em")
    @UpdateTimestamp
    private OffsetDateTime atualizadoEm;
}
