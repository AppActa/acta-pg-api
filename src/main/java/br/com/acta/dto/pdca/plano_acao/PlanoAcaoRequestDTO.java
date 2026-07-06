package br.com.acta.dto.pdca.plano_acao;

import br.com.acta.entity.enums.OrigemRegistro;
import br.com.acta.entity.enums.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlanoAcaoRequestDTO(
        @NotBlank(message = "{validation.planoAcao.nome.notblank}")
        @Size(max = 160, message = "{validation.planoAcao.nome.size}")
        String nome,

        @Size(max = 1000, message = "{validation.objetivo.size}")
        String objetivo,

        @NotNull(message = "{validation.prioridade.notnull}")
        Prioridade prioridade,

        @NotNull(message = "{validation.origem.notnull}")
        OrigemRegistro origem
) {
}
