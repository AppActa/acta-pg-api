package br.com.acta.dto.core.colaborador;

import br.com.acta.config.swagger.SwaggerExamples;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.telefone.TelefoneRequestDTO;
import br.com.acta.dto.core.usuario.UsuarioRequestDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;

public record ColaboradorRequestDTO(
        @Schema(description = "CPF do colaborador", example = SwaggerExamples.CPF, minLength = 11, maxLength = 11)
        @CPF(message = "{validation.colaborador.cpf.invalid}")
        @NotBlank(message = "{validation.colaborador.cpf.notblank}")
        @Size(min = 11, max = 11, message = "{validation.colaborador.cpf.size}")
        String cpf,

        @Schema(description = "Nome do colaborador", example = SwaggerExamples.NOME, maxLength = 160)
        @NotBlank(message = "{validation.colaborador.nome.notblank}")
        @Size(max = 160, message = "{validation.nome.size}")
        String nome,

        @Schema(description = "Cargo do colaborador", example = SwaggerExamples.CARGO, maxLength = 100)
        @NotBlank(message = "{validation.colaborador.cargo.notblank}")
        @Size(max = 100, message = "{validation.colaborador.cargo.size}")
        String cargo,

        @Schema(description = "Área do colaborador", example = SwaggerExamples.AREA, maxLength = 100)
        @NotBlank(message = "{validation.area.notblank}")
        @Size(max = 100, message = "{validation.area.size}")
        String area,

        @Schema(description = "Data de nascimento do colaborador", example = SwaggerExamples.DATA_NASCIMENTO, format = "yyyy-MM-dd", type = "string")
        @NotNull(message = "{validation.colaborador.dataNascimento.notnull}")
        @Past(message = "{validation.colaborador.dataNascimento.past}")
        LocalDate dataNascimento,

        @Schema(description = "Data de contratação do colaborador", example = SwaggerExamples.DATA_CONTRATACAO, format = "yyyy-MM-dd", type = "string")
        @NotNull(message = "{validation.colaborador.dataContratacao.notnull}")
        @PastOrPresent(message = "{validation.colaborador.dataContratacao.pastorpresent}")
        LocalDate dataContratacao,

        @Schema(description = "Permissão de gestor do colaborador", example = SwaggerExamples.PERMISSAO_GESTOR)
        @NotNull(message = "{validation.colaborador.permissaoGestor.notnull}")
        Boolean permissaoGestor,

        @ArraySchema(schema = @Schema(implementation = EmailRequestDTO.class), minItems = 1, uniqueItems = true, arraySchema = @Schema(description = "Lista de emails do colaborador"))
        @NotEmpty(message = "{validation.emails.notempty}")
        List<@Valid EmailRequestDTO> emails,

        @ArraySchema(schema = @Schema(implementation = TelefoneRequestDTO.class), minItems = 1, uniqueItems = true, arraySchema = @Schema(description = "Lista de telefones do colaborador"))
        @NotEmpty(message = "{validation.telefones.notempty}")
        List<@Valid TelefoneRequestDTO> telefones,

        @Schema(description = "Requisição de usuário do colaborador", implementation = UsuarioRequestDTO.class)
        @NotNull(message = "{validation.idUsuario.notnull}")
        @Valid
        UsuarioRequestDTO usuario,

        @Schema(description = "ID da empresa do colaborador", example = SwaggerExamples.ID_EMPRESA)
        @Positive(message = "{validation.idEmpresa.positive}")
        @NotNull(message = "{validation.idEmpresa.notnull}")
        Long idEmpresa
) {
}
