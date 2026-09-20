package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiResourceResponses;
import br.com.acta.common.config.swagger.annotation.ApiBadRequestResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.annotation.ApiUnsupportedMediaTypeResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.core.contato.email.EmailRequestDTO;
import br.com.acta.dto.core.contato.email.EmailResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "E-mails", description = SwaggerOpenapiDescriptions.EMAIL_CONTROLLER)
public interface EmailOpenapi {

    @Operation(summary = "Lista os e-mails de uma empresa")
    @ApiResponse(responseCode = "200", description = "E-mails encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmailResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<EmailResponseDTO>> buscarEmailEmpresa(Long idEmpresa);

    @Operation(summary = "Adiciona um e-mail a uma empresa")
    @ApiResponse(responseCode = "201", description = "E-mail adicionado", content = @Content(schema = @Schema(implementation = EmailResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<EmailResponseDTO> inserirEmailEmpresa(Long idEmpresa, EmailRequestDTO dto);

    @Operation(summary = "Exclui um e-mail de uma empresa")
    @ApiResponse(responseCode = "204", description = "E-mail excluído")
    @ApiResourceResponses
    ResponseEntity<Void> excluirEmailEmpresa(Long idEmpresa, Long idEmail);

    @Operation(summary = "Lista os e-mails de um colaborador")
    @ApiResponse(responseCode = "200", description = "E-mails encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = EmailResponseDTO.class))))
    @ApiAuthenticationResponses
    @ApiBadRequestResponse
    @ApiNotFoundResponse
    ResponseEntity<List<EmailResponseDTO>> buscarEmailColaborador(Long idColaborador);

    @Operation(summary = "Adiciona um e-mail a um colaborador")
    @ApiResponse(responseCode = "201", description = "E-mail adicionado", content = @Content(schema = @Schema(implementation = EmailResponseDTO.class)))
    @ApiResourceResponses
    @ApiUnsupportedMediaTypeResponse
    ResponseEntity<EmailResponseDTO> inserirEmailColaborador(Long idColaborador, EmailRequestDTO dto);

    @Operation(summary = "Exclui um e-mail de um colaborador")
    @ApiResponse(responseCode = "204", description = "E-mail excluído")
    @ApiResourceResponses
    ResponseEntity<Void> excluirEmailColaborador(Long idColaborador, Long idEmail);
}
