package br.com.acta.dto.core.convite;

import br.com.acta.common.config.swagger.examples.SwaggerRequestExamples;
import br.com.acta.entity.enums.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record ConviteRequestDTO(
        @Schema(description = "Nome do usuário convidado", example = SwaggerRequestExamples.NOME, maxLength = 160)
        @NotBlank(message = "{validation.usuario.nome.notblank}")
        @Size(max = 160, message = "{validation.nome.size}")
        String nome,

        @Schema(description = "E-mail de login e destino do convite", example = SwaggerRequestExamples.EMAIL, maxLength = 254)
        @NotBlank(message = "{validation.usuario.emailLogin.notblank}")
        @Size(max = 254, message = "{validation.usuario.emailLogin.size}")
        @Email(message = "{validation.usuario.emailLogin.invalid}")
        String email,

        @Schema(description = "Tipo do usuário convidado", example = SwaggerRequestExamples.TIPO_USUARIO, implementation = TipoUsuario.class)
        @NotNull(message = "{validation.usuario.tipo.notnull}")
        TipoUsuario tipo,

        @Schema(description = "CPF do colaborador convidado", example = SwaggerRequestExamples.CPF, minLength = 11, maxLength = 11)
        @CPF(message = "{validation.colaborador.cpf.invalid}")
        @NotBlank(message = "{validation.colaborador.cpf.notblank}")
        @Size(min = 11, max = 11, message = "{validation.colaborador.cpf.size}")
        String cpf,

        @Schema(description = "Cargo do colaborador convidado", example = SwaggerRequestExamples.CARGO, maxLength = 100)
        @NotBlank(message = "{validation.colaborador.cargo.notblank}")
        @Size(max = 100, message = "{validation.colaborador.cargo.size}")
        String cargo,

        @Schema(description = "Área do colaborador convidado", example = SwaggerRequestExamples.AREA, maxLength = 100)
        @NotBlank(message = "{validation.area.notblank}")
        @Size(max = 100, message = "{validation.area.size}")
        String area,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "Data de nascimento do colaborador convidado", example = SwaggerRequestExamples.DATA_NASCIMENTO, format = "yyyy-MM-dd", type = "string")
        @NotNull(message = "{validation.colaborador.dataNascimento.notnull}")
        @Past(message = "{validation.colaborador.dataNascimento.past}")
        LocalDate dataNascimento,

        @JsonFormat(pattern = "yyyy-MM-dd")
        @Schema(description = "Data de contratação do colaborador convidado", example = SwaggerRequestExamples.DATA_CONTRATACAO, format = "yyyy-MM-dd", type = "string")
        @NotNull(message = "{validation.colaborador.dataContratacao.notnull}")
        @PastOrPresent(message = "{validation.colaborador.dataContratacao.pastorpresent}")
        LocalDate dataContratacao,

        @Schema(description = "Permissão de gestor do colaborador convidado", example = SwaggerRequestExamples.PERMISSAO_GESTOR)
        @NotNull(message = "{validation.colaborador.permissaoGestor.notnull}")
        Boolean permissaoGestor
) {
}