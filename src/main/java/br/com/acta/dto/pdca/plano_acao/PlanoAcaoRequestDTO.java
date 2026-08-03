package br.com.acta.dto.pdca.plano_acao;

import br.com.acta.common.config.swagger.SwaggerExamples;
import br.com.acta.entity.enums.OrigemRegistro;
import br.com.acta.entity.enums.Prioridade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlanoAcaoRequestDTO(
        @Schema(description = "Nome do plano de ação", example = SwaggerExamples.NOME_PLANO_ACAO)
        @NotBlank(message = "{validation.planoAcao.nome.notblank}")
        @Size(max = 160, message = "{validation.planoAcao.nome.size}")
        String nome,

        @Schema(description = "Objetivo do plano de ação", example = SwaggerExamples.OBJETIVO_PLANO_ACAO)
        @Size(max = 1000, message = "{validation.objetivo.size}")
        String objetivo,

        @Schema(description = "Prioridade do plano de ação", implementation = Prioridade.class, example = SwaggerExamples.PRIORIDADE)
        @NotNull(message = "{validation.prioridade.notnull}")
        Prioridade prioridade,

        @Schema(description = "Origem do plano de ação", implementation = OrigemRegistro.class, example = SwaggerExamples.ORIGEM)
        @NotNull(message = "{validation.origem.notnull}")
        OrigemRegistro origem
) {
}
