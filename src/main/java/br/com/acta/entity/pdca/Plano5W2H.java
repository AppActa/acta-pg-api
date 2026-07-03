package br.com.acta.entity.pdca;

import br.com.acta.entity.base.auditoria.AuditoriaBase;
import br.com.acta.entity.core.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "plano_5w2h", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class Plano5W2H extends AuditoriaBase {
    @Column(name = "what_acao", nullable = false, columnDefinition = "TEXT")
    private String whatAcao;

    @Column(name = "why_justificativa", nullable = false, columnDefinition = "TEXT")
    private String whyJustificativa;

    @Column(name = "where_local", nullable = false, columnDefinition = "TEXT")
    private String whereLocal;

    @Column(name = "when_inicio")
    private LocalDate whenInicio;

    @Column(name = "when_fim", nullable = false)
    private LocalDate whenFim;

    @Column(name = "how_modo_execucao", nullable = false, columnDefinition = "TEXT")
    private String howModoExecucao;

    @Column(name = "how_much_custo", nullable = false, precision = 12, scale = 2)
    private BigDecimal howMuchCusto;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_who_responsavel", nullable = false)
    private Usuario whoResponsavel;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_plano_acao", nullable = false)
    private PlanoAcao planoAcao;
}
