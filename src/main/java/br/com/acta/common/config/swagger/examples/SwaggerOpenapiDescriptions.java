package br.com.acta.common.config.swagger.examples;

public final class SwaggerOpenapiDescriptions {
    public static final String AUTH_CONTROLLER = """
            Endpoints responsáveis pela autenticação e ativação de usuários.

            A API permite:

            - Consultar os dados do usuário autenticado;
            - Ativar um usuário a partir de uma identidade do Firebase.
            """;

    public static final String HEALTH_CONTROLLER = """
            Endpoint responsável por verificar a disponibilidade da API e do banco de dados.

            A verificação informa se a aplicação está disponível e se a conexão com o banco de dados está funcionando.
            """;

    public static final String CICLO_CONTROLLER = """
            Endpoints responsáveis pelo gerenciamento dos ciclos PDCA.
            
            Um ciclo representa a execução completa ou parcial das fases Plan, Do, Check e Act dentro de uma empresa.
            
            A API permite:
            
            - Criar um novo ciclo;
            - Consultar ciclos;
            - Filtrar ciclos por empresa, gestor e status;
            - Atualizar parcialmente dados de um ciclo aberto;
            - Controlar a progressão de status;
            - Cancelar um ciclo.
            
            (* Algumas operações dependem do status atual do ciclo. Ciclos encerrados, concluídos ou cancelados podem ter sua alteração bloqueada pelas regras de negócio)
            """;
}
