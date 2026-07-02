package br.com.acta.entity.pdca;

import br.com.acta.entity.base.TituloDescricaoBase;
import br.com.acta.entity.core.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Entity
@Table(name = "treinamento", schema = "pdca")
@Getter
@Setter
@NoArgsConstructor
public class Treinamento extends TituloDescricaoBase {
    @Column(name = "data_treinamento", nullable = false)
    private LocalDate dataTreinamento;

    @Column(name = "obrigatorio", nullable = false)
    @ColumnDefault("true")
    private Boolean obrigatorio;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ciclo", nullable = false)
    private Ciclo ciclo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_responsavel", nullable = false)
    private Usuario responsavel;

    @Column(name = "id_anexo_mongo")
    private Integer idAnexoMongo;
}
