package br.com.acta.entity.pdca;

import br.com.acta.entity.base.TituloDescricaoBase;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.Prioridade;
import br.com.acta.entity.enums.StatusTarefa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "tarefa", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class Tarefa extends TituloDescricaoBase {
    @Column(name = "prioridade", nullable = false)
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTarefa status;

    @Column(name = "data_inicio_real")
    private LocalDate dataInicioReal;

    @Column(name = "data_fim_prevista", nullable = false)
    private LocalDate dataFimPrevista;

    @Column(name = "data_fim_real")
    private LocalDate dataFimReal;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_plano_acao", nullable = false)
    private PlanoAcao planoAcao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_responsavel", nullable = false)
    private Usuario responsavel;

    @ManyToMany
    @JoinTable(name = "tarefa_dependencia", schema = "pdca",
        joinColumns = @JoinColumn(name = "id_tarefa"),
        inverseJoinColumns = @JoinColumn(name = "id_tarefa_dependencia")
    )
    private Set<Tarefa> dependencias;

    @ManyToMany(mappedBy = "dependencias")
    private Set<Tarefa> dependentes;
}
