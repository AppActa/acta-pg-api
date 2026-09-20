package br.com.acta.common.config.swagger.openapi;

import br.com.acta.common.config.firebase.FirebaseAuthFilter.FirebaseIdentity;
import br.com.acta.common.config.firebase.UsuarioAutenticado;
import br.com.acta.common.config.swagger.annotation.ApiAuthenticationResponses;
import br.com.acta.common.config.swagger.annotation.ApiConflictResponse;
import br.com.acta.common.config.swagger.annotation.ApiNotFoundResponse;
import br.com.acta.common.config.swagger.examples.SwaggerOpenapiDescriptions;
import br.com.acta.dto.auth.MeResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Autenticação", description = SwaggerOpenapiDescriptions.AUTH_CONTROLLER)
public interface AuthOpenapi {

    @Operation(summary = "Consulta o usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Usuário autenticado", content = @Content(schema = @Schema(implementation = MeResponseDTO.class)))
    @ApiAuthenticationResponses
    ResponseEntity<MeResponseDTO> me(UsuarioAutenticado usuario);

    @Operation(summary = "Ativa o usuário autenticado")
    @ApiResponse(responseCode = "200", description = "Usuário ativado", content = @Content(schema = @Schema(implementation = MeResponseDTO.class)))
    @ApiAuthenticationResponses
    @ApiNotFoundResponse
    @ApiConflictResponse
    ResponseEntity<MeResponseDTO> ativar(FirebaseIdentity identity);
}
