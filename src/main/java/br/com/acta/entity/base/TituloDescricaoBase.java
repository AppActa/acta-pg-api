package br.com.acta.entity.base;

import br.com.acta.entity.base.auditoria.AuditoriaBase;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class TituloDescricaoBase extends AuditoriaBase {
    @Column(name = "titulo", nullable = false, length = 160)
    private String titulo;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;
}
