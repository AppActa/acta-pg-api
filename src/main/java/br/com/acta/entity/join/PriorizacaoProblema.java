package br.com.acta.entity.join;

import br.com.acta.entity.base.auditoria.AuditoriaBase;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.pdca.Problema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "priorizacao_problema_usuario", schema = "pdca",
        uniqueConstraints = @UniqueConstraint(name = "priorizacao_problema_usuario_unique_0", columnNames = {"id_problema", "id_usuario"}))
@Getter
@Setter
@NoArgsConstructor
public class PriorizacaoProblema extends AuditoriaBase {
    @Column(name = "posicao", nullable = false)
    private Integer posicao;

    @Column(name = "peso_calculado", nullable = false, precision = 3, scale = 2)
    private BigDecimal pesoCalculado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_problema", nullable = false)
    private Problema problema;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
