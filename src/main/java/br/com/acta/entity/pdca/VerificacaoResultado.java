package br.com.acta.entity.pdca;

import br.com.acta.entity.base.auditoria.ImutavelBase;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusVerificacao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "verificacao_resultado", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class VerificacaoResultado extends ImutavelBase {
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVerificacao status;

    @Column(name = "resumo", nullable = false, columnDefinition = "TEXT")
    private String resumo;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "criado_por", nullable = false)
    private Usuario criadoPor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciclo", nullable = false)
    private Ciclo ciclo;

    @OneToMany(mappedBy = "verificacaoResultado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EfeitoSecundario> efeitosSecundarios;
}
