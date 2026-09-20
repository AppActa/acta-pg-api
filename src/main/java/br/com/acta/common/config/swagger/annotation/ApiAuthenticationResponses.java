package br.com.acta.common.config.swagger.annotation;

import br.com.acta.common.handler.ErroResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(
                responseCode = "401",
                description = "ID Token do Firebase ausente ou inválido",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponse.class))
        ),
        @ApiResponse(
                responseCode = "403",
                description = "Usuário sem permissão para acessar o recurso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErroResponse.class))
        )
})
public @interface ApiAuthenticationResponses {
}
