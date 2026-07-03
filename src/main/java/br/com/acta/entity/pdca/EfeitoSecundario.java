package br.com.acta.entity.pdca;

import br.com.acta.entity.base.auditoria.AuditoriaBase;
import br.com.acta.entity.enums.TipoEfeitoSecundario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "efeito_secundario", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class EfeitoSecundario extends AuditoriaBase {
    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "peso", nullable = false, precision = 3, scale = 2)
    private BigDecimal peso;

    @Column(name = "impacto_estimado", columnDefinition = "TEXT")
    private String impactoEstimado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_verificacao_resultado", nullable = false)
    private VerificacaoResultado verificacaoResultado;

    @Column(name = "tipo", length = 8)
    @Enumerated(EnumType.STRING)
    private TipoEfeitoSecundario tipo;
}
