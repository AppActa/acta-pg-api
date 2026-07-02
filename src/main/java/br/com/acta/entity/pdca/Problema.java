package br.com.acta.entity.pdca;

import br.com.acta.entity.base.TituloDescricaoBase;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.OrigemRegistro;
import br.com.acta.entity.enums.StatusProblema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "problema", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class Problema extends TituloDescricaoBase {
    @Column(name = "peso", nullable = false, precision = 3, scale = 2)
    private BigDecimal peso;

    @Column(name = "status", nullable = false, length = 40)
    @Enumerated(EnumType.STRING)
    private StatusProblema status;

    @Column(name = "origem", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrigemRegistro origem;

    @Column(name = "persistente", nullable = false)
    private Boolean persistente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciclo", nullable = false)
    private Ciclo ciclo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_problema_pai")
    private Problema problemaPai;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "criado_por", nullable = false)
    private Usuario criadoPor;
}
