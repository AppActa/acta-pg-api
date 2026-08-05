package br.com.acta.entity.pdca;

import br.com.acta.entity.base.auditoria.AuditoriaBase;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.OrigemRegistro;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "causa_raiz", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class CausaRaiz extends AuditoriaBase {
    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "origem", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrigemRegistro origem;

    @Column(name = "aceita", nullable = false)
    private Boolean aceita;

    @Column(name = "validada_em")
    private OffsetDateTime validadaEm;

    @Column(name = "principal", nullable = false)
    private Boolean principal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciclo", nullable = false)
    private Ciclo ciclo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_problema", nullable = false)
    private Problema problema;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plano_acao")
    private PlanoAcao planoAcao;

    @Column(name = "id_5_porques_mongo")
    private String id5PorquesMongo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "validada_por")
    private Usuario validadaPor;
}