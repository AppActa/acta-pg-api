package br.com.acta.common.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenapiConfig {
    @Bean
    public OpenAPI actaOpenAPI(){
        return new OpenAPI()
                .info(apiInfo())
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth", bearerAuthScheme()));
    }

    private Info apiInfo() {
        return new Info()
                .title("ACTA API")
                .description("API de suporte para a metodologia do PDCA (identificação de problemas, análise de causa raiz, implementação de plano de ações, verificação de resultados e padronização)")
                .version("1.0.0")
                .contact(new Contact().name("Equipe ACTA").email("acta.institutojef@gmail.com"))
                .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT"));
    }

    private SecurityScheme bearerAuthScheme(){
        return new SecurityScheme()
                .name("bearerAuth")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .description("Informe o token JWT obtido em POST /auth/login");
    }
}
