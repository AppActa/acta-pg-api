package br.com.acta.entity.pdca;

import br.com.acta.entity.base.TituloDescricaoBase;
import br.com.acta.entity.core.Empresa;
import br.com.acta.entity.core.Usuario;
import br.com.acta.entity.enums.StatusCiclo;
import br.com.acta.entity.join.UsuarioCiclo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "ciclo", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class Ciclo extends TituloDescricaoBase {
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusCiclo status;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_estimada_fim", nullable = false)
    private LocalDate dataEstimadaFim;

    @Column(name = "data_fim_real")
    private LocalDate dataFimReal;

    @OneToMany(mappedBy = "ciclo", fetch = FetchType.LAZY)
    private Set<UsuarioCiclo> colaboradores;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empresa", nullable = false)
    private Empresa empresa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_responsavel", nullable = false)
    private Usuario gestor;
}
