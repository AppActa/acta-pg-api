package br.com.acta.entity.pdca;

import br.com.acta.entity.base.ModelBase;
import br.com.acta.entity.core.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "alerta_prazo", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class AlertaPrazo extends ModelBase {
    @Column(name = "mensagem", nullable = false, updatable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "enviado_em", updatable = false)
    private OffsetDateTime enviadoEm;

    @Column(name = "lido_em")
    private OffsetDateTime lidoEm;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tarefa", nullable = false)
    private Tarefa tarefa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_usuario_destino", nullable = false)
    private Usuario usuarioDestino;
}