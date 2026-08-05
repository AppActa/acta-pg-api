package br.com.acta.entity.pdca;

import br.com.acta.entity.base.auditoria.AuditoriaBase;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusMeta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "meta", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class Meta extends AuditoriaBase {
    @Column(name = "objetivo", nullable = false, columnDefinition = "TEXT")
    private String objetivo;

    @Column(name = "valor_base", precision = 15, scale = 2)
    private BigDecimal valorBase;

    @Column(name = "valor_alvo", precision = 15, scale = 2)
    private BigDecimal valorAlvo;

    @Column(name = "unidade", length = 30)
    private String unidadeMedida;

    @Column(name = "prazo", nullable = false)
    private LocalDate prazo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusMeta status;

    @Column(name = "prioridade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Column(name = "area", length = 100)
    private String area;

    @Column(name = "categoria", length = 100)
    private String categoria;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciclo", nullable = false)
    private Ciclo ciclo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_plano_acao", nullable = false)
    private PlanoAcao planoAcao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "meta_responsavel", schema = "pdca",
            joinColumns = @JoinColumn(name = "id_meta"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<Usuario> responsaveis;
}
