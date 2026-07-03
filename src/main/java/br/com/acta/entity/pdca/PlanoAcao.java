package br.com.acta.entity.pdca;

import br.com.acta.entity.base.auditoria.AuditoriaBase;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.OrigemRegistro;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusPlanoAcao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "plano_acao", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class PlanoAcao extends AuditoriaBase {
    @Column(name = "nome", nullable = false, length = 160)
    private String nome;

    @Column(name = "objetivo", columnDefinition = "TEXT")
    private String objetivo;

    @Column(name = "prioridade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusPlanoAcao status;

    @Column(name = "origem", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrigemRegistro origem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciclo", nullable = false)
    private Ciclo ciclo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "criado_por", nullable = false)
    private Usuario criadoPor;
}
